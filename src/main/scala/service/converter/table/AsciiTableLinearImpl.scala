package service.converter.table

/**
 * A concrete implementation of `AsciiTable` that maps greyscale intensity values to ASCII characters
 * using a linear distribution.
 *
 * This class uses a provided string of ASCII characters, where the characters are arranged from the
 * darkest (`characters(0)`) to the brightest (`characters(characters.length - 1)`). Each greyscale
 * intensity value (0–255) is mapped to a character based on its position in the greyscale range.
 *
 * @param characters a string containing the ASCII characters to use for mapping, ordered by intensity
 *                   (dark to light)
 */
class AsciiTableLinearImpl(characters: String) extends AsciiTable{

  /**
   * The scale factor used to map greyscale values to character indices.
   */
  private val scaleFactor = 255 / characters.length

  /**
   * Maps a greyscale intensity value to an ASCII character using a linear distribution.
   *
   * @param greyscale the greyscale intensity value (0–255) to map
   * @return the ASCII character corresponding to the greyscale value
   * @throws IllegalArgumentException if the greyscale value is outside the range 0–255
   */
  override def getAsciiForGreyscale(greyscale: Int): Char = {

    if (greyscale < 0 || greyscale > 255)
      throw new IllegalArgumentException("Greyscale value must be between 0 and 255")

    val index = math.min(greyscale / scaleFactor, characters.length - 1)
    characters(index)
  }

}
