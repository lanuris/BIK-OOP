package service.converter.image

import model.Image
import service.converter.table.AsciiTable

/**
 * A concrete implementation of `ImageGreyscaleConverterAscii` for converting greyscale images to ASCII art.
 *
 * This class processes an `Image[Int]` where each pixel represents a greyscale intensity value (0–255)
 * and converts it into an `Image[Char]` where each pixel corresponds to an ASCII character.
 * The conversion uses an `AsciiTable` to map greyscale intensity ranges to ASCII characters.
 */
class ImageGreyscaleConverterAsciiImpl extends ImageGreyscaleConverterAscii {

  /**
   * Converts a greyscale image into an ASCII art image using the provided ASCII table.
   *
   * @param image the input `Image[Int]` containing greyscale pixel data
   * @param table an `AsciiTable` that maps greyscale intensity ranges to ASCII characters
   * @return a new `Image[Char]` where each pixel is represented by an ASCII character
   * @throws Exception if an invalid greyscale value is encountered
   */
  override def convert(image: Image[Int], table: AsciiTable): Image[Char] = {
    // Create a 2D array based on the image dimensions
    val asciiArray = Array.ofDim[Char](image.getHeight, image.getWidth)

    // Use pixelIterator to traverse through the greyscale values
    image.pixelIterator.foreach { case ((x, y), greyscale) =>
      if (isValidGreyscale(greyscale)) {
        // Get the corresponding ASCII character from the table
        val asciiChar = table.getAsciiForGreyscale(greyscale)
        // Assign the character to the correct position in the 2D array
        asciiArray(y)(x) = asciiChar
      } else {
        throw new Exception(s"Invalid greyscale value at position ($x, $y)")
      }
    }
    Image(asciiArray)
  }

  /**
   * Throws a `NotImplementedError` to enforce the usage of the `convert(image: Image[Int], table: AsciiTable)` method.
   *
   * @param image the input `Image[Int]` (not used)
   * @return nothing, always throws an error
   */
  override def convert(image: Image[Int]): Image[Char] = {
    throw new NotImplementedError("Use convert(image: Image[Int], table: AsciiTable) instead.")
  }

  /**
   * Validates a greyscale intensity value to ensure it is within the valid range (0–255).
   *
   * @param greyscale the greyscale intensity value to validate
   * @return `true` if the value is valid, `false` otherwise
   */
  private def isValidGreyscale(greyscale: Int): Boolean = {
    greyscale >= 0 && greyscale <= 255
  }
}
