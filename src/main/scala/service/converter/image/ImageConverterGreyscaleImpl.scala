package service.converter.image

import model.{Image, Pixel}


/**
 * A concrete implementation of `ImageConverterGreyscale` for converting a color image to greyscale.
 *
 * This class processes an `Image[Pixel]` object, where each pixel contains RGB color data,
 * and converts it to an `Image[Int]`, where each pixel represents a greyscale intensity value.
 * The greyscale value is calculated using the standard weighted formula for luminance:
 * `0.3 * red + 0.59 * green + 0.11 * blue`.
 */
class ImageConverterGreyscaleImpl extends ImageConverterGreyscale {

  /**
   * Converts a color image to a greyscale image.
   *
   * @param image the input `Image[Pixel]` containing RGB color pixel data
   * @return a new `Image[Int]` where each pixel represents a greyscale intensity value
   * @throws Exception if an invalid pixel is detected (e.g., RGB values out of range)
   */
  def convert(image: Image[Pixel]): Image[Int] = {
    // Create a 2D array based on the image dimensions
    val greyscaleArray = Array.ofDim[Int](image.getHeight, image.getWidth)

    // Use pixelIterator to traverse through the image pixels
    image.pixelIterator.foreach { case ((x, y), pixel) =>
      if (isValidPixel(pixel)) {
        // Convert each pixel to greyscale
        val greyscale = toGreyscale(pixel.red, pixel.green, pixel.blue)
        // Assign the greyscale value to the correct position in the 2D array
        greyscaleArray(y)(x) = greyscale
      } else {
        throw new Exception("Invalid pixel detected at position (" + x + ", " + y + ")")
      }
    }
    Image(greyscaleArray)
  }

  /**
   * Validates a pixel to ensure that its RGB values are within the range of 0 to 255.
   *
   * @param pixel the pixel to validate
   * @return `true` if all RGB values are within the valid range, `false` otherwise
   */
  private def isValidPixel(pixel: Pixel): Boolean = {
    // Check if each color component is within the valid range 0 to 255
    val validRed = pixel.red >= 0 && pixel.red <= 255
    val validGreen = pixel.green >= 0 && pixel.green <= 255
    val validBlue = pixel.blue >= 0 && pixel.blue <= 255
    // Return true if all components are within the valid range
    validRed && validGreen && validBlue
  }

  /**
   * Converts RGB color values to a greyscale intensity value using a weighted formula.
   *
   * @param red   the red component of the pixel
   * @param green the green component of the pixel
   * @param blue  the blue component of the pixel
   * @return the calculated greyscale intensity value
   */
  private def toGreyscale(red: Int, green: Int, blue: Int): Int = {
    (0.3 * red + 0.59 * green + 0.11 * blue).toInt
  }
}

