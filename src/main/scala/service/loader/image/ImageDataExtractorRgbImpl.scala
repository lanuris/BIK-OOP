package service.loader.image

import model.{Image, Pixel}

import java.awt.image.BufferedImage

/**
 * A concrete implementation of `ImageDataExtractor` for extracting RGB pixel data from a `BufferedImage`.
 *
 * This class processes a `BufferedImage` to extract its pixel data and converts it into an
 * `Image[Pixel]` object. Each pixel's RGB color is decomposed into its red, green, and blue
 * components and stored in a 2D array of `Pixel`.
 */
class ImageDataExtractorRgbImpl extends ImageDataExtractor {

  /**
   * Extracts RGB pixel data from the provided `BufferedImage` and converts it into an `Image[Pixel]`.
   *
   * @param bufferedImage the `BufferedImage` to extract pixel data from
   * @return an `Image[Pixel]` representation of the input image
   */
  override def extract(bufferedImage: BufferedImage): Image[Pixel] = {

    val width = bufferedImage.getWidth
    val height = bufferedImage.getHeight

    val pixelArray = Array.ofDim[Pixel](height, width)
    // Loop over every pixel in the BufferedImage
    for (y <- 0 until height; x <- 0 until width) {
      // Extract the RGB value from the BufferedImage at (x, y)
      val rgb = bufferedImage.getRGB(x, y)

      // Extract the red, green, and blue components from the RGB integer
      val red = (rgb >> 16) & 0xFF
      val green = (rgb >> 8) & 0xFF
      val blue = rgb & 0xFF
      // Create a Pixel and store it in the array
      pixelArray(y)(x) = Pixel(red, green, blue)
    }

    Image(pixelArray)
  }
}
