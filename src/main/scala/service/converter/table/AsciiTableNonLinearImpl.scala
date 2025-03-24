package service.converter.table

/**
 * A concrete implementation of `AsciiTable` that maps greyscale intensity values to ASCII characters
 * using a non-linear distribution.
 *
 * This class uses a predefined mapping (`nonLinearTable`) for specific greyscale ranges.
 * For values below a certain threshold (200), the darkest character (`@`) is returned. For values
 * above the threshold, a non-linear scale maps greyscale intensities to corresponding ASCII characters.
 */
class AsciiTableNonLinearImpl extends AsciiTable {

  /**
   * A predefined mapping of ASCII characters for non-linear ranges.
   * The mapping associates specific indices with ASCII characters in decreasing intensity:
   * - `0` maps to `@`
   * - `9` maps to a space (` `)
   */
  private val nonLinearTable = Map(
    0 -> '@',
    1 -> '#',
    2 -> '*',
    3 -> '+',
    4 -> '=',
    5 -> '-',
    6 -> ':',
    7 -> '.',
    8 -> ',',
    9 -> ' '
  )

  /**
   * Maps a greyscale intensity value to an ASCII character using a non-linear distribution.
   *
   * - For values less than or equal to 200, the character `@` is returned.
   * - For values above 200, the mapping is scaled to distribute the remaining greyscale range
   *   (201–255) across the characters in the `nonLinearTable`.
   *
   * @param greyscale the greyscale intensity value (0–255) to map
   * @return the ASCII character corresponding to the greyscale value
   * @throws IllegalArgumentException if the greyscale value is outside the valid range
   */
  override def getAsciiForGreyscale(greyscale: Int): Char = {

    if (greyscale < 0 || greyscale > 255)
      throw new IllegalArgumentException("Greyscale value must be between 0 and 255")

    if (greyscale <= 200) '@'
    else {
      val scaleFactor = (255 - 200) / (nonLinearTable.size - 1)
      nonLinearTable((greyscale - 200) / scaleFactor)
    }
  }

}
