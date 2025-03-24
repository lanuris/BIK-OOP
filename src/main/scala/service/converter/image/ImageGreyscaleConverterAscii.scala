package service.converter.image

import model.Image
import service.converter.table.AsciiTable

/**
 * A specialized trait for converting a greyscale image to an ASCII art representation.
 *
 * This trait extends `ImageConverter`, transforming an `Image[Int]` where each pixel represents
 * a greyscale intensity value (0–255) into an `Image[Char]` where each pixel corresponds to a
 * character from a provided ASCII table. The conversion uses a mapping between greyscale intensity
 * and ASCII characters, typically provided by an `AsciiTable`.
 */
trait ImageGreyscaleConverterAscii extends ImageConverter[Int, Char]{

  /**
   * Converts a greyscale image into an ASCII art image using the provided ASCII table.
   *
   * @param image the input `Image[Int]` containing greyscale pixel data
   * @param table an `AsciiTable` that maps greyscale intensity ranges to ASCII characters
   * @return a new `Image[Char]` where each pixel is represented by an ASCII character
   */
  def convert(image: Image[Int], table: AsciiTable): Image[Char]

}
