package console.controller

import model.ImageModel
import service.converter.table.AsciiTable
import service.filter.image.ImageFilter
import service.loader.image.{ImageFileLoader, ImageRandomGenerator}

/**
 * A trait defining the main interface for controlling image processing operations.
 *
 * The `Controller` trait provides methods to handle various actions such as loading images,
 * applying filters, converting images to ASCII art, and exporting results. It supports
 * interactions with both the console and files.
 */
trait Controller {

  /**
   * Displays the main page or menu of the application.
   */
  def showMainPage(): Unit

  /**
   * Displays the main page or menu of the application.
   */
  def showHelp(): Unit

  /**
   * Loads an image from a file, converts it to greyscale, and updates the model.
   *
   * @param path   the file path of the image to load
   * @param model  the `ImageModel` to update with the loaded and converted image
   * @param loader the `ImageFileLoader` to use for loading the image
   */
  def loadAndConvertToGreyscale(path: String, model: ImageModel, loader: ImageFileLoader) : Unit

  /**
   * Generates a random image, converts it to greyscale, and updates the model.
   *
   * @param model                the `ImageModel` to update with the generated and converted image
   * @param randomImageGenerator the `ImageRandomGenerator` to use for generating the image
   */
  def generateAndConvertToGreyscale(model: ImageModel, randomImageGenerator: ImageRandomGenerator): Unit

  /**
   * Applies a filter to the greyscale image in the model.
   *
   * @param model  the `ImageModel` containing the greyscale image
   * @param filter the `ImageFilter[Int]` to apply to the greyscale image
   */
  def applyFilterToGreyscaleImage(model: ImageModel, filter: ImageFilter[Int]): Unit

  /**
   * Converts a greyscale image to ASCII art and exports it to a file.
   *
   * @param path       the file path to export the ASCII art
   * @param model      the `ImageModel` containing the greyscale image
   * @param asciiTable the `AsciiTable` used for mapping greyscale values to ASCII characters
   */
  def convertGreyscaleToAsciiAndExportToFile (path: String, model: ImageModel, asciiTable: AsciiTable) : Unit

  /**
   * Converts a greyscale image to ASCII art and prints it to the console.
   *
   * @param model      the `ImageModel` containing the greyscale image
   * @param asciiTable the `AsciiTable` used for mapping greyscale values to ASCII characters
   */
  def convertGreyscaleToAsciiAndExportToConsole(model: ImageModel, asciiTable: AsciiTable): Unit

  /**
   * Displays an error message.
   *
   * @param error the error message to display
   */
  def showError (error: String) : Unit 

}
