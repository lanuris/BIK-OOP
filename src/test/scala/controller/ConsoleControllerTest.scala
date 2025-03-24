package controller


import org.scalatest.funsuite.AnyFunSuite
import org.mockito.Mockito._
import org.mockito.ArgumentMatchers._
import console.controller.ConsoleController
import console.view.page.generic.{ErrorResponse, SuccessResponse}
import model.{Image, ImageModel, Pixel}
import service.converter.image.{ImageConverterGreyscale, ImageGreyscaleConverterAscii}
import service.converter.table.AsciiTable
import service.exporter.text.TextExporter
import service.filter.image.ImageFilter
import service.loader.image.{ImageFileLoader, ImageRandomGenerator}

import scala.collection.mutable.ArrayBuffer

class ConsoleControllerTest extends AnyFunSuite {

  test("showMainPage should render the main page") {
    val mockTextExporter = mock(classOf[TextExporter])
    val mockImageConverterGreyscale = mock(classOf[ImageConverterGreyscale])
    val mockImageGreyscaleConverterAscii = mock(classOf[ImageGreyscaleConverterAscii])

    val controller = new ConsoleController(
      mockTextExporter,
      mockImageConverterGreyscale,
      mockImageGreyscaleConverterAscii
    )

    controller.showMainPage()
    verify(mockTextExporter, times(1)).exportFile(any[String])
  }

  test("showHelp should render the help page") {
    val mockTextExporter = mock(classOf[TextExporter])
    val mockImageConverterGreyscale = mock(classOf[ImageConverterGreyscale])
    val mockImageGreyscaleConverterAscii = mock(classOf[ImageGreyscaleConverterAscii])

    val controller = new ConsoleController(
      mockTextExporter,
      mockImageConverterGreyscale,
      mockImageGreyscaleConverterAscii
    )

    controller.showHelp()
    verify(mockTextExporter, times(1)).exportFile(any[String])
  }

  test("loadAndConvertToGreyscale should load and convert image to greyscale") {
    val mockTextExporter = mock(classOf[TextExporter])
    val mockImageConverterGreyscale = mock(classOf[ImageConverterGreyscale])
    val mockImageGreyscaleConverterAscii = mock(classOf[ImageGreyscaleConverterAscii])
    val mockLoader = mock(classOf[ImageFileLoader])

    val controller = new ConsoleController(
      mockTextExporter,
      mockImageConverterGreyscale,
      mockImageGreyscaleConverterAscii
    )

    val dummyPath = "dummy/path"
    val dummyPixelImage = mock(classOf[Image[Pixel]])
    val dummyGreyscaleImage = mock(classOf[Image[Int]])
    val model = new ImageModel

    when(mockLoader.load(dummyPath)).thenReturn(dummyPixelImage)
    when(mockImageConverterGreyscale.convert(dummyPixelImage)).thenReturn(dummyGreyscaleImage)

    controller.loadAndConvertToGreyscale(dummyPath, model, mockLoader)

    assert(model.imagePixel.contains(dummyPixelImage))
    assert(model.imageGreyscale.contains(dummyGreyscaleImage))
    verify(mockTextExporter, atLeastOnce()).exportFile(any[String])
  }

  test("generateAndConvertToGreyscale should generate and convert image to greyscale") {
    val mockTextExporter = mock(classOf[TextExporter])
    val mockRandomImageGenerator = mock(classOf[ImageRandomGenerator])
    val mockImageConverterGreyscale = mock(classOf[ImageConverterGreyscale])
    val mockImageGreyscaleConverterAscii = mock(classOf[ImageGreyscaleConverterAscii])

    val controller = new ConsoleController(
      mockTextExporter,
      mockImageConverterGreyscale,
      mockImageGreyscaleConverterAscii
    )

    val dummyPixelImage = mock(classOf[Image[Pixel]])
    val dummyGreyscaleImage = mock(classOf[Image[Int]])
    val model = new ImageModel

    when(mockRandomImageGenerator.load()).thenReturn(dummyPixelImage)
    when(mockImageConverterGreyscale.convert(dummyPixelImage)).thenReturn(dummyGreyscaleImage)

    controller.generateAndConvertToGreyscale(model, mockRandomImageGenerator)

    assert(model.imagePixel.contains(dummyPixelImage))
    assert(model.imageGreyscale.contains(dummyGreyscaleImage))
    verify(mockTextExporter, atLeastOnce()).exportFile(any[String])
  }

  test("applyFilterToGreyscaleImage should apply filter to greyscale image") {
    val mockTextExporter = mock(classOf[TextExporter])
    val mockImageConverterGreyscale = mock(classOf[ImageConverterGreyscale])
    val mockImageGreyscaleConverterAscii = mock(classOf[ImageGreyscaleConverterAscii])
    val mockFilter = mock(classOf[ImageFilter[Int]])

    val controller = new ConsoleController(
      mockTextExporter,
      mockImageConverterGreyscale,
      mockImageGreyscaleConverterAscii
    )

    val dummyGreyscaleImage = mock(classOf[Image[Int]])
    val filteredImage = mock(classOf[Image[Int]])
    val model = new ImageModel

    model.imageGreyscale = Some(dummyGreyscaleImage)
    when(mockFilter.applyFilter(dummyGreyscaleImage)).thenReturn(filteredImage)

    controller.applyFilterToGreyscaleImage(model, mockFilter)

    assert(model.imageGreyscale.contains(filteredImage))
    verify(mockTextExporter, times(1)).exportFile(any[String])
  }

  test("convertGreyscaleToAsciiAndExportToConsole should convert and display ASCII on console") {
    val mockTextExporter = mock(classOf[TextExporter])
    val mockImageConverterGreyscale = mock(classOf[ImageConverterGreyscale])
    val mockImageGreyscaleConverterAscii = mock(classOf[ImageGreyscaleConverterAscii])
    val mockAsciiTable = mock(classOf[AsciiTable])

    val controller = new ConsoleController(
      mockTextExporter,
      mockImageConverterGreyscale,
      mockImageGreyscaleConverterAscii
    )

    val dummyGreyscaleImage = mock(classOf[Image[Int]])
    val dummyAsciiImage = mock(classOf[Image[Char]])
    val model = new ImageModel

    model.imageGreyscale = Some(dummyGreyscaleImage)
    when(mockImageGreyscaleConverterAscii.convert(dummyGreyscaleImage, mockAsciiTable)).thenReturn(dummyAsciiImage)

    controller.convertGreyscaleToAsciiAndExportToConsole(model, mockAsciiTable)

    assert(model.imageChar.contains(dummyAsciiImage))
    verify(mockTextExporter, atLeastOnce()).exportFile(any[String])
  }
}



