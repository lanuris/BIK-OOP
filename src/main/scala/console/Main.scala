package console

import console.controller.ConsoleController
import console.view.ConsoleView
import model.ImageModel
import service.converter.image.{ImageConverterGreyscaleImpl, ImageGreyscaleConverterAsciiImpl}
import service.exporter.text.StdOutputExporter

/**
 * Entry point for the application. This program converts an image to a greyscale representation
 * and then further transforms it into an ASCII representation. The output is displayed on the console.
 */
object Main {

  /**
   * The main method initializes the application components and runs the program.
   *
   * @param args Command-line arguments
   */
  def main(args: Array[String]): Unit = {

    // Initialize the image model that acts as the central data store for image processing.
    val model = new ImageModel

    // Exporter to handle text-based output (standard console output in this case).
    val stdOutput = new StdOutputExporter

    // Service to convert an image to its greyscale representation.
    val imageConverterGreyscale = new ImageConverterGreyscaleImpl

    // Service to convert a greyscale image to its ASCII representation.
    val imageGreyscaleConverterAscii = new ImageGreyscaleConverterAsciiImpl

    // Initialize the controller to coordinate operations between model, view, and services.
    val controller = new ConsoleController(stdOutput, imageConverterGreyscale, imageGreyscaleConverterAscii)

    // Create the view that interacts with the user through the console.
    val view = new ConsoleView(controller)

    // Run the program using the provided arguments and initialized model.
    view.run(args, model)
  }
}
