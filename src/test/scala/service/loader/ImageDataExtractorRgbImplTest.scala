package service.loader

import org.scalatest.funsuite.AnyFunSuite
import model.{Image, Pixel}
import java.awt.image.BufferedImage
import java.awt.Color
import service.loader.image.ImageDataExtractorRgbImpl

class ImageDataExtractorRgbImplTest extends AnyFunSuite {

  test("extract from a 2x2 BufferedImage with known colors") {
    val width = 2
    val height = 2
    val bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB)

    // Set known colors
    bufferedImage.setRGB(0, 0, Color.RED.getRGB)
    bufferedImage.setRGB(1, 0, Color.GREEN.getRGB)
    bufferedImage.setRGB(0, 1, Color.BLUE.getRGB)
    bufferedImage.setRGB(1, 1, Color.WHITE.getRGB)

    val extractor = new ImageDataExtractorRgbImpl
    val image = extractor.extract(bufferedImage)

    val expectedPixels = Array(
      Array(Pixel(255, 0, 0), Pixel(0, 255, 0)),
      Array(Pixel(0, 0, 255), Pixel(255, 255, 255))
    )

    assert(image.getWidth == width)
    assert(image.getHeight == height)

    for (y <- 0 until height; x <- 0 until width) {
      val extractedPixel = image.getPixel(x, y)
      val expectedPixel = expectedPixels(y)(x)
      assert(extractedPixel == expectedPixel, s"Pixel at ($x, $y) does not match")
    }
  }
  

  test("extract from a BufferedImage with width != height") {
    val width = 3
    val height = 2
    val bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB)

    // Set random colors
    bufferedImage.setRGB(0, 0, Color.YELLOW.getRGB)
    bufferedImage.setRGB(1, 0, Color.CYAN.getRGB)
    bufferedImage.setRGB(2, 0, Color.MAGENTA.getRGB)
    bufferedImage.setRGB(0, 1, Color.BLACK.getRGB)
    bufferedImage.setRGB(1, 1, Color.GRAY.getRGB)
    bufferedImage.setRGB(2, 1, Color.ORANGE.getRGB)

    val extractor = new ImageDataExtractorRgbImpl
    val image = extractor.extract(bufferedImage)

    val expectedPixels = Array(
      Array(Pixel(255, 255, 0), Pixel(0, 255, 255), Pixel(255, 0, 255)),
      Array(Pixel(0, 0, 0), Pixel(128, 128, 128), Pixel(255, 200, 0))
    )

    assert(image.getWidth == width)
    assert(image.getHeight == height)

    for (y <- 0 until height; x <- 0 until width) {
      val extractedPixel = image.getPixel(x, y)
      val expectedPixel = expectedPixels(y)(x)
      assert(extractedPixel == expectedPixel, s"Pixel at ($x, $y) does not match")
    }
  }

  test("extract from a BufferedImage with transparency") {
    val width = 2
    val height = 2
    val bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB)

    // Set colors with alpha channel
    bufferedImage.setRGB(0, 0, (128 << 24) | Color.RED.getRGB)
    bufferedImage.setRGB(1, 0, (64 << 24) | Color.GREEN.getRGB)
    bufferedImage.setRGB(0, 1, (192 << 24) | Color.BLUE.getRGB)
    bufferedImage.setRGB(1, 1, (255 << 24) | Color.WHITE.getRGB)

    val extractor = new ImageDataExtractorRgbImpl
    val image = extractor.extract(bufferedImage)

    // Alpha channel is ignored in the extractor
    val expectedPixels = Array(
      Array(Pixel(255, 0, 0), Pixel(0, 255, 0)),
      Array(Pixel(0, 0, 255), Pixel(255, 255, 255))
    )

    assert(image.getWidth == width)
    assert(image.getHeight == height)

    for (y <- 0 until height; x <- 0 until width) {
      val extractedPixel = image.getPixel(x, y)
      val expectedPixel = expectedPixels(y)(x)
      assert(extractedPixel == expectedPixel, s"Pixel at ($x, $y) does not match")
    }
  }
  
  test("extract from a 1x1 BufferedImage with a known color") {
    val width = 1
    val height = 1
    val bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB)

    // Set a known color, for example, pure red
    bufferedImage.setRGB(0, 0, Color.RED.getRGB)

    val extractor = new ImageDataExtractorRgbImpl
    val image = extractor.extract(bufferedImage)

    val expectedPixel = Pixel(255, 0, 0)

    assert(image.getWidth == width)
    assert(image.getHeight == height)

    val extractedPixel = image.getPixel(0, 0)
    assert(extractedPixel == expectedPixel, s"Pixel at (0, 0) does not match expected value")
  }
}
