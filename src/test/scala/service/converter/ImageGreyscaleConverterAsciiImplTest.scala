package service.converter

import org.scalatest.funsuite.AnyFunSuite
import org.scalatestplus.mockito.MockitoSugar
import org.mockito.Mockito._
import model.Image
import service.converter.table.AsciiTable
import service.converter.image.ImageGreyscaleConverterAsciiImpl

class ImageGreyscaleConverterAsciiImplTest extends AnyFunSuite with MockitoSugar {

  test("convert valid greyscale image to ASCII") {
    // Define a simple greyscale image
    val greyscalePixels = Array(
      Array(0, 128, 255)
    )
    val image = Image(greyscalePixels)

    // Mock the AsciiTable
    val asciiTable = mock[AsciiTable]
    // Define the mapping from greyscale values to ASCII characters
    when(asciiTable.getAsciiForGreyscale(0)).thenReturn(' ')
    when(asciiTable.getAsciiForGreyscale(128)).thenReturn('.')
    when(asciiTable.getAsciiForGreyscale(255)).thenReturn('#')

    val converter = new ImageGreyscaleConverterAsciiImpl
    val asciiImage = converter.convert(image, asciiTable)

    // Expected ASCII image
    val expectedAsciiPixels = Array(
      Array(' ', '.', '#')
    )

    // Assert that the ASCII image matches the expected output
    assert(asciiImage.getHeight == 1)
    assert(asciiImage.getWidth == 3)
    asciiImage.pixelIterator.foreach { case ((x, y), char) =>
      assert(char == expectedAsciiPixels(y)(x))
    }
  }

  test("convert image with invalid greyscale value (>255)") {
    val greyscalePixels = Array(
      Array(256) // Invalid greyscale value
    )
    val image = Image(greyscalePixels)
    val asciiTable = mock[AsciiTable]
    val converter = new ImageGreyscaleConverterAsciiImpl

    val exception = intercept[Exception] {
      converter.convert(image, asciiTable)
    }
    assert(exception.getMessage.contains("Invalid greyscale value at position (0, 0)"))
  }

  test("convert image with invalid greyscale value (<0)") {
    val greyscalePixels = Array(
      Array(-1) // Invalid greyscale value
    )
    val image = Image(greyscalePixels)
    val asciiTable = mock[AsciiTable]
    val converter = new ImageGreyscaleConverterAsciiImpl

    val exception = intercept[Exception] {
      converter.convert(image, asciiTable)
    }
    assert(exception.getMessage.contains("Invalid greyscale value at position (0, 0)"))
  }
  
  test("convert image with greyscale values at boundaries") {
    val greyscalePixels = Array(
      Array(0, 255)
    )
    val image = Image(greyscalePixels)

    val asciiTable = mock[AsciiTable]
    when(asciiTable.getAsciiForGreyscale(0)).thenReturn(' ')
    when(asciiTable.getAsciiForGreyscale(255)).thenReturn('#')

    val converter = new ImageGreyscaleConverterAsciiImpl
    val asciiImage = converter.convert(image, asciiTable)

    val expectedAsciiPixels = Array(
      Array(' ', '#')
    )

    assert(asciiImage.getHeight == 1)
    assert(asciiImage.getWidth == 2)
    asciiImage.pixelIterator.foreach { case ((x, y), char) =>
      assert(char == expectedAsciiPixels(y)(x))
    }
  }

  test("convert single-pixel image") {
    val greyscalePixels = Array(
      Array(128)
    )
    val image = Image(greyscalePixels)
    val asciiTable = mock[AsciiTable]
    when(asciiTable.getAsciiForGreyscale(128)).thenReturn('.')

    val converter = new ImageGreyscaleConverterAsciiImpl
    val asciiImage = converter.convert(image, asciiTable)

    assert(asciiImage.getHeight == 1)
    assert(asciiImage.getWidth == 1)
    val pixel = asciiImage.pixelIterator.next()
    assert(pixel._2 == '.')
  }

  test("convert image with multiple greyscale values") {
    val greyscalePixels = Array(
      Array(0, 64, 128, 192, 255)
    )
    val image = Image(greyscalePixels)
    val asciiTable = mock[AsciiTable]
    when(asciiTable.getAsciiForGreyscale(0)).thenReturn(' ')
    when(asciiTable.getAsciiForGreyscale(64)).thenReturn('-')
    when(asciiTable.getAsciiForGreyscale(128)).thenReturn('+')
    when(asciiTable.getAsciiForGreyscale(192)).thenReturn('*')
    when(asciiTable.getAsciiForGreyscale(255)).thenReturn('#')

    val converter = new ImageGreyscaleConverterAsciiImpl
    val asciiImage = converter.convert(image, asciiTable)

    val expectedAsciiPixels = Array(
      Array(' ', '-', '+', '*', '#')
    )

    asciiImage.pixelIterator.foreach { case ((x, y), char) =>
      assert(char == expectedAsciiPixels(y)(x))
    }
  }

  test("attempt to call convert(image: Image[Int]) without AsciiTable") {
    val image = mock[Image[Int]]
    val converter = new ImageGreyscaleConverterAsciiImpl

    val exception = intercept[NotImplementedError] {
      converter.convert(image)
    }
    assert(exception.getMessage.contains("Use convert(image: Image[Int], table: AsciiTable) instead."))
  }
}
