package console.parser

/**
 * A utility object that defines available command-line options and organizes them into a set.
 *
 * The commands are categorized as follows:
 * - General commands: `--help`
 * - Table-related commands: `--table`, `--table-custom`
 * - Image-related commands: `--image`, `--image-random`
 * - Output-related commands: `--output-console`, `--output-file`
 * - Filter-related commands: `--flip`, `--scale`, `--rotate`, `--invert`
 */
object Commands {
  
  val Help = "--help"
  val Table = "--table"
  val TableCustom = "--table-custom"
  val Image = "--image"
  val ImageRandom = "--image-random"
  val OutputConsole = "--output-console"
  val OutputFile = "--output-file"
  val Flip = "--flip"
  val Scale = "--scale"
  val Rotate = "--rotate"
  val Invert = "--invert"

  val AllCommands: Set[String] = Set(
    Help, Table, TableCustom, Image, ImageRandom,
    OutputConsole, OutputFile, Flip, Scale, Rotate, Invert
  )
}

/**
 * Represents the parsed structure of command-line arguments.
 *
 * @param tableCommand  the table-related command, if provided (e.g., `--table`, `--table-custom`)
 * @param imageCommand  the image-related command (e.g., `--image`, `--image-random`), must be present
 * @param otherCommands other commands and their associated parameters
 */
case class ParsedCommands(
                           tableCommand: Option[(String, Array[String])],
                           imageCommand: (String, Array[String]),
                           otherCommands: Seq[(String, Array[String])]
                         )

/**
 * A class for parsing command-line arguments and organizing them into structured commands.
 *
 * This parser validates the provided arguments and ensures that only recognized and valid
 * combinations of commands are accepted.
 *
 * - A single table command (`--table` or `--table-custom`) is optional but mutually exclusive.
 * - A single image command (`--image` or `--image-random`) is required.
 * - Any additional commands are captured as "other commands."
 *
 * @throws IllegalArgumentException if invalid or conflicting commands are provided
 */
class CommandLineParser {
  
  import Commands._

  /**
   * Parses the given command-line arguments into structured commands.
   *
   * @param args the raw array of command-line arguments
   * @return a `ParsedCommands` object representing the structured commands
   * @throws IllegalArgumentException if invalid or conflicting commands are provided
   */
  def parseArguments(args: Array[String]): ParsedCommands = {
    if (args.contains(Help)) {
      if (args.length == 1) {
        return ParsedCommands(None, (Help, Array.empty), Seq.empty)
      } else {
        throw new IllegalArgumentException(s"$Help must be the only parameter.")
      }
    }

    val arguments = args.mkString(" ").split("--").map("--" + _).tail

    var tableCommand: Option[(String, Array[String])] = None
    var imageCommand: Option[(String, Array[String])] = None
    val otherCommands = scala.collection.mutable.ListBuffer.empty[(String, Array[String])]

    arguments.foreach { command =>
      val parts = command.trim.split("\\s+")
      val mainCommand = "--" + parts.head.stripPrefix("--")
      val params = parts.tail

      mainCommand match {
        case Table | TableCustom =>
          if (tableCommand.isDefined) {
            throw new IllegalArgumentException(s"Only one table command ($Table or $TableCustom) can be used.")
          }
          tableCommand = Some((mainCommand, params))

        case Image | ImageRandom =>
          if (imageCommand.isDefined) {
            throw new IllegalArgumentException(s"Only one image command ($Image or $ImageRandom) can be used.")
          }
          imageCommand = Some((mainCommand, params))

        case cmd if AllCommands.contains(cmd) =>
          otherCommands += ((mainCommand, params))

        case invalidCommand =>
          throw new IllegalArgumentException(s"Invalid command: $invalidCommand")
      }
    }

    if (imageCommand.isEmpty) {
      throw new IllegalArgumentException(s"An image command ($Image or $ImageRandom) must be provided.")
    }

    ParsedCommands(tableCommand, imageCommand.get, otherCommands.toList)
  }
}
