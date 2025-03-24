package console.view.page.concrete

import console.view.page.TextPage
import console.parser.Commands

/**
 * A concrete implementation of `TextPage` for rendering the help page.
 *
 * The `HelpPage` class provides detailed guidance on how to use the application, including
 * descriptions of available commands, filters, and usage examples.
 */
class HelpPage extends TextPage {

  /**
   * Renders the help page with instructions and examples for using the application.
   *
   * @return a string containing the help content, including available commands and filters
   */
  override def render(): String = {
    var result = ""

    result += "----- HELP -----\n\n"

    result += "GENERAL COMMANDS:\n"
    result += s"${Commands.Help} - Display this help message.\n\n"

    result += "TABLE COMMANDS:\n"
    result += s"${Commands.Table} [linear|non-linear|default] - Specify the type of ASCII table.\n"
    result += s"${Commands.TableCustom} <custom_characters> - Provide a custom ASCII table.\n\n"

    result += "IMAGE COMMANDS:\n"
    result += s"${Commands.Image} <image_path> - Load an image from the specified file.\n"
    result += s"${Commands.ImageRandom} - Generate a random image.\n\n"

    result += "OUTPUT COMMANDS:\n"
    result += s"${Commands.OutputConsole} - Render the output to the console.\n"
    result += s"${Commands.OutputFile} <output_path> - Export the output to a specified file (TXT format only).\n\n"

    result += "FILTER COMMANDS:\n"
    result += s"${Commands.Flip} <x|y|xy|yx> - Flip the image horizontally, vertically, or both.\n"
    result += s"${Commands.Scale} <width:height> - Scale the image to the specified aspect ratio.\n"
    result += s"${Commands.Rotate} <angle> - Rotate the image by a multiple of 90 degrees.\n"
    result += s"${Commands.Invert} - Invert the colors of the image.\n\n"

    result += "USAGE EXAMPLE:\n"
    result += "run --image example.jpg --rotate 90 --flip x --scale 1:2 --output-console\n\n"

    result += "NOTES:\n"
    result += "- Only PNG and JPG formats are supported as input.\n"
    result += "- Only TXT format is supported for output files.\n"

    result
  }
}
