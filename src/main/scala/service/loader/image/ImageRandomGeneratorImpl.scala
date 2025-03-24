package service.loader.image

import model.{Image, Pixel}

/**
 * A concrete implementation of `ImageRandomGenerator` for generating random images.
 *
 * This class generates a random `Image[Pixel]` with dimensions and pixel colors
 * chosen randomly. The image dimensions are determined by random values between
 * 100 and 150 for both width and height. Each pixel is assigned a random RGB color value.
 */
class ImageRandomGeneratorImpl extends ImageRandomGenerator {

  /**
   * Generates a random image with random dimensions and pixel colors.
   *
   * @return an `Image[Pixel]` object containing randomly generated pixel data
   */
  override def load(): Image[Pixel] = {
    val random = new scala.util.Random
    val width = random.between(100, 150)
    val height = random.between(100, 150)

    val pixelArray = Array.ofDim[Pixel](width, height)

    for (x <- 0 until width; y <- 0 until height) {
      val red = random.nextInt(256)
      val green = random.nextInt(256)
      val blue = random.nextInt(256)
      pixelArray(x)(y) = Pixel(red, green, blue)
    }

    Image(pixelArray)
  }
}
