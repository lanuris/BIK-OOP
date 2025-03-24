package console.controller

import console.view.page.TextPage
import console.view.page.concrete.{HelpPage, ImagePage, MainPage}
import console.view.page.generic.{ErrorResponse, SuccessResponse}
import model.{Image, ImageModel, Pixel}
import service.converter.image.{ImageConverterGreyscale, ImageGreyscaleConverterAscii}
import service.converter.table.AsciiTable
import service.exporter.text.{FileOutputExporterTxt, TextExporter}
import service.filter.image.ImageFilter
import service.loader.image.{ImageFileLoader, ImageRandomGenerator}


/**
 * A concrete implementation of the `Controller` trait for managing console-based image processing operations.
 *
 * The `ConsoleController` class provides methods for loading, generating, converting, filtering, and exporting
 * images through a console interface. It supports operations such as displaying help and error messages,
 * processing images, and rendering ASCII art either to the console or to a file.
 *
 * @param stdOutputExporter            an instance of `TextExporter` for exporting text to the standard output
 * @param imageConverterGreyscale      an instance of `ImageConverterGreyscale` for converting images to greyscale
 * @param imageGreyscaleConverterAscii an instance of `ImageGreyscaleConverterAscii` for converting greyscale images to ASCII art
 */
class ConsoleController  (
                           private val stdOutputExporter: TextExporter,
                           private val imageConverterGreyscale: ImageConverterGreyscale,
                           private val imageGreyscaleConverterAscii: ImageGreyscaleConverterAscii
                       ) extends Controller {

  /**
   * Displays the main page or menu of the application.
   */
  override def showMainPage(): Unit = render(new MainPage)

  /**
   * Displays the main page or menu of the application.
   */
  override def showHelp(): Unit = render(new HelpPage)

  /**
   * Loads an image from the specified path and converts it to greyscale.
   *
   * @param path the file path of the image
   * @param model the `ImageModel` to update with the loaded and converted image
   * @param loader the `ImageFileLoader` to use for loading the image
   */
  override def loadAndConvertToGreyscale(path: String, model: ImageModel, loader: ImageFileLoader): Unit = {
      loadImage(path, model, loader)
      convertToGreyscale(model)
  }

  /**
   * Generates a random image and converts it to greyscale.
   *
   * @param model the `ImageModel` to update with the generated and converted image
   * @param randomImageGenerator the `ImageRandomGenerator` to use for generating the image
   */
  override def generateAndConvertToGreyscale(model: ImageModel, randomImageGenerator: ImageRandomGenerator): Unit = {
    generateImage(model, randomImageGenerator)
    convertToGreyscale(model)
  }

  /**
   * Applies a filter to the greyscale image in the model.
   *
   * @param model the `ImageModel` containing the greyscale image
   * @param filter the `ImageFilter[Int]` to apply to the greyscale image
   */
  override def applyFilterToGreyscaleImage(model: ImageModel, filter: ImageFilter[Int]): Unit = {
    model.imageGreyscale match {
      case Some(greyscaleImage) =>
        model.imageGreyscale = Some(filter.applyFilter(greyscaleImage))
        render(new SuccessResponse("Image Filter Applied"))
      case None => showError("No greyscale image available to apply filter.")
    }
  }

  /**
   * Converts the greyscale image to ASCII and exports it to a file.
   *
   * @param path the file path to export the ASCII art
   * @param model the `ImageModel` containing the greyscale image
   * @param asciiTable the `AsciiTable` used for mapping greyscale values to ASCII characters
   */
  override def convertGreyscaleToAsciiAndExportToFile(path: String, model: ImageModel, asciiTable: AsciiTable): Unit = {
    convertToAscii(model, asciiTable)
    exportAsciiToFile(path, model)
  }

  /**
   * Converts the greyscale image to ASCII and displays it on the console.
   *
   * @param model the `ImageModel` containing the greyscale image
   * @param asciiTable the `AsciiTable` used for mapping greyscale values to ASCII characters
   */
  override def convertGreyscaleToAsciiAndExportToConsole(model: ImageModel, asciiTable: AsciiTable): Unit = {
    convertToAscii(model, asciiTable)
    exportAsciiToConsole(model)
  }

  /**
   * Displays an error message to the console.
   *
   * @param message the error message to display
   */
  override def showError(message: String): Unit = {
    render(new ErrorResponse(message))
  }

  /**
   * Renders a page to the console output.
   *
   * @param page the `TextPage` to render
   */
  private def render(page: TextPage): Unit = {
    val output = page.render()
    stdOutputExporter.exportFile(output)
  }

  /**
   * Loads an image from the specified path.
   *
   * @param path the file path of the image
   * @param model the `ImageModel` to update with the loaded image
   * @param loader the `ImageFileLoader` to use for loading the image
   */
  private def loadImage(path: String, model: ImageModel, loader: ImageFileLoader): Unit = {
    model.imagePixel = Some(loader.load(path))
    render(new SuccessResponse("Image Loaded"))
  }

  /**
   * Generates a random image.
   *
   * @param model the `ImageModel` to update with the generated image
   * @param randomImageGenerator the `ImageRandomGenerator` to use for generating the image
   */
  private def generateImage(model: ImageModel, randomImageGenerator: ImageRandomGenerator): Unit = {
    model.imagePixel = Some(randomImageGenerator.load())
    render(new SuccessResponse("Random Image Generated"))
  }

  /**
   * Converts the loaded image to greyscale.
   *
   * @param model the `ImageModel` containing the loaded image
   */
  private def convertToGreyscale(model: ImageModel): Unit = {
    model.imagePixel match {
      case Some(pixelImage) =>
        model.imageGreyscale = Some(imageConverterGreyscale.convert(pixelImage))
        render(new SuccessResponse("Image Converted to Greyscale"))
      case None => showError("No image loaded to convert to greyscale.")
    }
  }

  /**
   * Converts the greyscale image to ASCII characters.
   *
   * @param model the `ImageModel` containing the greyscale image
   * @param asciiTable the `AsciiTable` used for mapping greyscale values to ASCII characters
   */
  private def convertToAscii(model: ImageModel, asciiTable: AsciiTable): Unit = {
    model.imageGreyscale match {
      case Some(greyscaleImage) =>
        model.imageChar = Some(imageGreyscaleConverterAscii.convert(greyscaleImage, asciiTable))
        render(new SuccessResponse("Image Converted to ASCII"))
      case None => showError("No greyscale image available to convert to ASCII.")
    }
  }

  /**
   * Exports the ASCII representation of the image to a file.
   *
   * @param path the file path to export the ASCII art
   * @param model the `ImageModel` containing the ASCII representation
   */
  private def exportAsciiToFile(path: String, model: ImageModel): Unit = {
    model.imageChar match {
      case Some(charImage) =>
        val exporter = new FileOutputExporterTxt(path)
        exporter.exportFile(new ImagePage(charImage).render())
        render(new SuccessResponse("ASCII Image Exported to File"))
      case None => showError("No ASCII image available to export.")
    }
  }

  /**
   * Displays the ASCII representation of the image on the console.
   *
   * @param model the `ImageModel` containing the ASCII representation
   */
  private def exportAsciiToConsole(model: ImageModel): Unit = {
    model.imageChar match {
      case Some(charImage) => render(new ImagePage(charImage))
      case None => showError("No ASCII image available to display.")
    }
  }
}
