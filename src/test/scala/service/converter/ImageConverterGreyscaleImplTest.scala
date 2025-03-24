package service.converter

import org.scalatest.funsuite.AnyFunSuite
import model.{Image, Pixel}
import service.converter.image.ImageConverterGreyscaleImpl

class ImageConverterGreyscaleImplTest extends AnyFunSuite {

  test("convert valid image to greyscale") {
    val pixels = Array(
      Array(Pixel(255, 0, 0), Pixel(0, 255, 0)),
      Array(Pixel(0, 0, 255), Pixel(255, 255, 0))
    )
    val image = Image(pixels)
    val converter = new ImageConverterGreyscaleImpl
    val greyscaleImage = converter.convert(image)
    val expectedGreyscaleValues = Array(
      Array(76, 150),
      Array(28, 226)
    )
    assert(greyscaleImage.getHeight == 2)
    assert(greyscaleImage.getWidth == 2)
    greyscaleImage.pixelIterator.foreach { case ((x, y), value) =>
      assert(value == expectedGreyscaleValues(y)(x))
    }
  }

  test("convert image with invalid pixel (value > 255)") {
    val pixels = Array(
      Array(Pixel(256, 0, 0)) // Invalid red value
    )
    val image = Image(pixels)
    val converter = new ImageConverterGreyscaleImpl
    val exception = intercept[Exception] {
      converter.convert(image)
    }
    assert(exception.getMessage.contains("Invalid pixel detected at position (0, 0)"))
  }

  test("convert image with invalid pixel (negative value)") {
    val pixels = Array(
      Array(Pixel(-1, 0, 0)) // Negative red value
    )
    val image = Image(pixels)
    val converter = new ImageConverterGreyscaleImpl
    val exception = intercept[Exception] {
      converter.convert(image)
    }
    assert(exception.getMessage.contains("Invalid pixel detected at position (0, 0)"))
  }
  
  test("convert one-pixel image") {
    val pixels = Array(
      Array(Pixel(123, 45, 67))
    )
    val image = Image(pixels)
    val converter = new ImageConverterGreyscaleImpl
    val greyscaleImage = converter.convert(image)
    val expectedGreyscale = (0.3 * 123 + 0.59 * 45 + 0.11 * 67).toInt
    assert(greyscaleImage.getHeight == 1)
    assert(greyscaleImage.getWidth == 1)
    assert(greyscaleImage.pixelIterator.next()._2 == expectedGreyscale)
  }

  test("convert black pixel (0, 0, 0)") {
    val pixels = Array(
      Array(Pixel(0, 0, 0))
    )
    val image = Image(pixels)
    val converter = new ImageConverterGreyscaleImpl
    val greyscaleImage = converter.convert(image)
    assert(greyscaleImage.pixelIterator.next()._2 == 0)
  }

  test("convert white pixel (255, 255, 255)") {
    val pixels = Array(
      Array(Pixel(255, 255, 255))
    )
    val image = Image(pixels)
    val converter = new ImageConverterGreyscaleImpl
    val greyscaleImage = converter.convert(image)
    assert(greyscaleImage.pixelIterator.next()._2 == 255)
  }
}
