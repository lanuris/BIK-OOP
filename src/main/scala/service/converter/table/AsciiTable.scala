package service.converter.table

/**
 * A trait for mapping greyscale intensity values to ASCII characters.
 *
 * This trait provides a single method, `getAsciiForGreyscale`, which maps a greyscale intensity value
 * (typically in the range 0–255) to a corresponding ASCII character. The mapping logic can vary based
 * on specific implementations.
 */
trait AsciiTable {

  /**
   * Maps a greyscale intensity value to an ASCII character.
   *
   * @param greyscale the greyscale intensity value to map
   * @return the ASCII character corresponding to the greyscale value
   * @throws IllegalArgumentException if the greyscale value is outside the valid range
   */
  def getAsciiForGreyscale(greyscale: Int): Char
}
