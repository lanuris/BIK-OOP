package model

/**
 * A representation of a single pixel with RGB color values.
 *
 * Each `Pixel` contains three components: red, green, and blue, represented as integers.
 * The class provides methods for equality comparison, generating a hash code, and string representation.
 *
 * @param red   the red component of the pixel (0–255)
 * @param green the green component of the pixel (0–255)
 * @param blue  the blue component of the pixel (0–255)
 */
class Pixel(val red:Int, val green:Int, val blue:Int){

  /**
   * Compares this pixel to another object for equality.
   *
   * @param obj the object to compare with
   * @return `true` if the object is a `Pixel` with the same RGB values, `false` otherwise
   */
  override def equals(obj: Any): Boolean = obj match {
    case other: Pixel => red == other.red && green == other.green && blue == other.blue
    case _ => false
  }

  /**
   * Generates a hash code for this pixel based on its RGB values.
   *
   * @return the hash code for the pixel
   */
  override def hashCode(): Int = (red, green, blue).##

  /**
   * Returns a string representation of the pixel in the format `Pixel(red, green, blue)`.
   *
   * @return a string describing the pixel
   */
  override def toString: String = s"Pixel($red, $green, $blue)"
  
}
