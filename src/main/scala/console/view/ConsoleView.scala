package console.view

import console.controller.Controller
import console.parser.{CommandLineParser, Commands}
import model.ImageModel
import service.converter.table.{AsciiTable, AsciiTableLinearCustomImpl, AsciiTableLinearImpl, AsciiTableNonLinearImpl}
import service.filter.image.{ImageFilterAspectRatioImpl, ImageFilterFlipImpl, ImageFilterRotateImpl, ImageGreyscaleFilterInvertImpl}
import service.loader.image.{ImageFileLoader, ImageFileLoaderJpgImpl, ImageFileLoaderPngImpl, ImageRandomGeneratorImpl}

/**
 * A console-based interface for managing and processing image-related operations.
 *
 * The `ConsoleView` class provides functionality to parse command-line arguments, invoke the appropriate
 * controller methods, and handle various image processing tasks, including:
 * - Loading images or generating random images
 * - Applying filters
 * - Converting images to ASCII art
 * - Exporting ASCII art to the console or a file
 *
 * @param controller the `Controller` instance that coordinates image processing operations
 */
class ConsoleView(controller: Controller) {

  /**
   * The command-line parser used for parsing and validating user input.
   */
  private val parser = new CommandLineParser

  /**
   * Main application function that starts the view and processes commands.
   *
   * @param args the command-line arguments provided by the user
   * @param model the `ImageModel` to store and manipulate image data
   */
  def run(args: Array[String], model: ImageModel): Unit = {
    controller.showMainPage()
    try {
      processCommand(args, model)
    } catch {
      case e: Exception =>
        controller.showError(e.getMessage)
    }
  }

  /**
   * Parses and processes the command-line arguments.
   *
   * @param args the command-line arguments
   * @param model the `ImageModel` used for storing image data
   * @throws IllegalArgumentException if invalid or conflicting commands are detected
   */
  private def processCommand(args: Array[String], model: ImageModel): Unit = {
    val commands = parser.parseArguments(args)

    // Handle help command immediately
    if (commands.imageCommand._1 == Commands.Help) {
      controller.showHelp()
      return
    }

    val asciiTable = processTableCommand(commands.tableCommand)
    processImageCommand(commands.imageCommand, model)

    // Process other commands in the order they were provided
    commands.otherCommands.foreach {
      case (Commands.OutputConsole, params) => handleOutputConsole(params, model, asciiTable)
      case (Commands.OutputFile, params)    => handleOutputFile(params, model, asciiTable)
      case (Commands.Flip, params)          => handleFlip(params, model)
      case (Commands.Scale, params)         => handleScale(params, model)
      case (Commands.Rotate, params)        => handleRotate(params, model)
      case (Commands.Invert, params)        => handleInvert(params, model)
      case (invalidCommand, _)              => throw new IllegalArgumentException(s"Invalid command: $invalidCommand")
    }
  }

  /**
   * Processes table-related commands to select or configure the ASCII table.
   *
   * @param tableCommand the table-related command and its parameters, if any
   * @return the configured `AsciiTable` instance
   */
  private def processTableCommand(tableCommand: Option[(String, Array[String])]): AsciiTable = {
    tableCommand match {
      case Some((Commands.Table, Array(tableType))) =>
        tableType match {
          case "linear" =>
            new AsciiTableLinearImpl("$@B%8&WM#*oahkbdpqwmZO0QLCJUYXzcvunxrjft/|()1{}[]?-_+~<>i!lI;:,\"^`'. ")
          case "non-linear" =>
            new AsciiTableNonLinearImpl
          case "default" =>
            new AsciiTableLinearImpl("@%#*+=-:. ")
          case _ =>
            throw new IllegalArgumentException(s"No table found for type '$tableType'.")
        }
      case Some((Commands.TableCustom, Array(customTable))) =>
        new AsciiTableLinearCustomImpl(customTable)
      case Some((command, _)) =>
        throw new IllegalArgumentException(s"$command requires exactly one parameter.")
      case None =>
        new AsciiTableLinearImpl("@%#*+=-:. ") // Default table
    }
  }

  /**
   * Processes image-related commands to load or generate an image.
   *
   * @param imageCommand the image-related command and its parameters
   * @param model        the `ImageModel` used for storing the loaded or generated image
   * @throws IllegalArgumentException if the parameters are invalid
   */
  private def processImageCommand(imageCommand: (String, Array[String]), model: ImageModel): Unit = {
    imageCommand match {
      case (Commands.Image, Array(imagePath)) =>
        val imageLoader = getLoader(imagePath)
        controller.loadAndConvertToGreyscale(imagePath, model, imageLoader)
      case (Commands.ImageRandom, Array()) =>
        controller.generateAndConvertToGreyscale(model, new ImageRandomGeneratorImpl)
      case (command, _) =>
        throw new IllegalArgumentException(s"$command has invalid parameters.")
    }
  }

  /**
   * Determines the appropriate loader based on the file extension of the image path.
   *
   * @param imagePath the path of the image file
   * @return the corresponding `ImageFileLoader`
   * @throws IllegalArgumentException if no loader is available for the given format
   */
  private def getLoader(imagePath: String): ImageFileLoader = {
    val loaders: Map[String, ImageFileLoader] = Map(
      "jpg" -> new ImageFileLoaderJpgImpl,
      "png" -> new ImageFileLoaderPngImpl
    )
    val extension = imagePath.split("\\.").last.toLowerCase
    loaders.getOrElse(extension, throw new IllegalArgumentException(s"No loader available for format: $extension"))
  }

  /**
   * Validates the number of parameters for a given command.
   *
   * @param params the parameters to validate
   * @param expectedCount the expected number of parameters
   * @param command the name of the command for better error reporting
   * @throws IllegalArgumentException if the number of parameters does not match the expected count
   */
  private def validateParams(params: Array[String], expectedCount: Int, command: String): Unit = {
    if (params.length != expectedCount) {
      throw new IllegalArgumentException(s"$command requires exactly $expectedCount parameter(s).")
    }
  }

  /**
   * Handles the command to output the ASCII art to the console.
   *
   * @param params     the parameters associated with the command
   * @param model      the `ImageModel` containing the image data
   * @param asciiTable the ASCII table used for conversion
   * @throws IllegalArgumentException if invalid parameters are passed
   */
  private def handleOutputConsole(params: Array[String], model: ImageModel, asciiTable: AsciiTable): Unit = {
    validateParams(params, 0, Commands.OutputConsole)
    controller.convertGreyscaleToAsciiAndExportToConsole(model, asciiTable)
  }

  /**
   * Handles the command to output the ASCII art to a file.
   *
   * @param params     the parameters associated with the command
   * @param model      the `ImageModel` containing the image data
   * @param asciiTable the ASCII table used for conversion
   * @throws IllegalArgumentException if invalid parameters are passed
   */
  private def handleOutputFile(params: Array[String], model: ImageModel, asciiTable: AsciiTable): Unit = {
    validateParams(params, 1, Commands.OutputFile)
    val outputPath = params.head
    controller.convertGreyscaleToAsciiAndExportToFile(outputPath, model, asciiTable)
  }

  /**
   * Handles the command to apply a flip filter to the image.
   *
   * @param params the parameters indicating the flip direction (x, y, or xy)
   * @param model  the `ImageModel` containing the image data
   * @throws IllegalArgumentException if an invalid direction is specified
   */
  private def handleFlip(params: Array[String], model: ImageModel): Unit = {
    validateParams(params, 1, Commands.Flip)
    val flipDirection = params.head

    val (flipX, flipY) = flipDirection match {
      case "x" => (true, false)
      case "y" => (false, true)
      case "xy" | "yx" => (true, true)
      case _ => throw new IllegalArgumentException("Unknown flip direction in Flip filter")
    }

    controller.applyFilterToGreyscaleImage(model, new ImageFilterFlipImpl[Int](flipX, flipY))
  }

  /**
   * Handles the command to scale the image while preserving the aspect ratio.
   *
   * @param params the parameters specifying the aspect ratio in the format 'x:y'
   * @param model  the `ImageModel` containing the image data
   * @throws IllegalArgumentException if invalid or non-integer values are provided
   */
  private def handleScale(params: Array[String], model: ImageModel): Unit = {
    validateParams(params, 1, Commands.Scale)
    val scaleValue = params.head

    val (x, y) = scaleValue.split(":") match {
      case Array(width, height) =>
        try {
          (width.toInt, height.toInt)
        } catch {
          case _: NumberFormatException =>
            throw new Exception("Aspect ratio values must be integers.")
        }
      case _ =>
        throw new Exception("Invalid aspect ratio format. It should be in 'x:y' format.")
    }

    controller.applyFilterToGreyscaleImage(model, new ImageFilterAspectRatioImpl[Int](x, y))
  }

  /**
   * Handles the command to rotate the image.
   *
   * @param params the parameters specifying the rotation angle in degrees
   * @param model  the `ImageModel` containing the image data
   * @throws IllegalArgumentException if the parameter is not an integer
   */
  private def handleRotate(params: Array[String], model: ImageModel): Unit = {
    validateParams(params, 1, Commands.Rotate)
    val angle = try {
      params.head.toInt
    } catch {
      case _: NumberFormatException =>
        throw new IllegalArgumentException("Rotate filter parameter must be an integer.")
    }
    controller.applyFilterToGreyscaleImage(model,new ImageFilterRotateImpl[Int](angle))
  }

  /**
   * Handles the command to invert the grayscale values of the image.
   *
   * @param params the parameters associated with the command (none required for invert)
   * @param model  the `ImageModel` containing the image data
   * @throws IllegalArgumentException if unexpected parameters are passed
   */
  private def handleInvert(params: Array[String], model: ImageModel): Unit = {
    validateParams(params, 0, Commands.Invert)
    controller.applyFilterToGreyscaleImage(model, new ImageGreyscaleFilterInvertImpl)
  }

}
