package service.loader

import org.scalatest.funsuite.AnyFunSuite
import model.{Image, Pixel}
import service.loader.image.ImageRandomGeneratorImpl

class ImageRandomGeneratorImplTest extends AnyFunSuite {

  test("generateRandom returns an Image with dimensions between 100 and 150") {
    val generator = new ImageRandomGeneratorImpl
    val image = generator.load()

    val width = image.getWidth
    val height = image.getHeight

    assert(width >= 100 && width < 150, s"Width $width is not between 100 and 150")
    assert(height >= 100 && height < 150, s"Height $height is not between 100 and 150")
  }

  test("generateRandom returns an Image with valid pixels") {
    val generator = new ImageRandomGeneratorImpl
    val image = generator.load()

    for (y <- 0 until image.getHeight; x <- 0 until image.getWidth) {
      val pixel = image.getPixel(x, y)
      assert(pixel.red >= 0 && pixel.red <= 255, s"Pixel red value ${pixel.red} at ($x, $y) is out of bounds")
      assert(pixel.green >= 0 && pixel.green <= 255, s"Pixel green value ${pixel.green} at ($x, $y) is out of bounds")
      assert(pixel.blue >= 0 && pixel.blue <= 255, s"Pixel blue value ${pixel.blue} at ($x, $y) is out of bounds")
    }
  }

  test("generateRandom generates different images on subsequent calls") {
    val generator = new ImageRandomGeneratorImpl
    val image1 = generator.load()
    val image2 = generator.load()

    assert(image1.getWidth != image2.getWidth || image1.getHeight != image2.getHeight || !image1.equals(image2),
      "generateRandom should generate different images on subsequent calls")
  }

  
  test("generateRandom produces pixels with full range of possible values") {
    val generator = new ImageRandomGeneratorImpl
    val image = generator.load()

    var foundMinValue = false
    var foundMaxValue = false

    for (y <- 0 until image.getHeight; x <- 0 until image.getWidth) {
      val pixel = image.getPixel(x, y)
      if (pixel.red == 0 || pixel.green == 0 || pixel.blue == 0) foundMinValue = true
      if (pixel.red == 255 || pixel.green == 255 || pixel.blue == 255) foundMaxValue = true
    }

    assert(foundMinValue, "At least one pixel should have a color component with value 0")
    assert(foundMaxValue, "At least one pixel should have a color component with value 255")
  }
}
