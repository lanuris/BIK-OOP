package service.filter.image

import model.Image
import scala.reflect.ClassTag

/**
 * A concrete implementation of `ImageFilter` that rotates an image by a specified angle.
 *
 * This class supports rotation of images by multiples of 90 degrees: 0, 90, 180, or 270 degrees.
 * The rotation is performed clockwise, and if the angle is not a multiple of 90, an exception is thrown.
 *
 * @param angle the rotation angle in degrees (must be a multiple of 90)
 * @tparam T the type of the pixel data in the image
 */
class ImageFilterRotateImpl[T: ClassTag] (angle: Int) extends ImageFilter[T] {

  /**
   * Applies the rotation filter to the given image.
   *
   * @param item the input image to be rotated
   * @return a new `Image[T]` object with the specified rotation applied
   * @throws IllegalArgumentException if the image has zero width or height, or if the angle is invalid
   */
  override def applyFilter(item: Image[T]): Image[T] = {

    if (item.getHeight == 0 || item.getWidth == 0)
      throw new IllegalArgumentException("Image size error")

    // Normalize angle to values between 0 and 270
    val normalizedAngle = ((angle % 360) + 360) % 360

    val rotatedPixels: Image[T] = normalizedAngle match {
      case 0 =>
        item // No rotation
      case 90 =>
        rotate90(item)
      case 180 =>
        rotate180(item)
      case 270 =>
        rotate270(item)
      case _ =>
//        rotateImage(item, normalizedAngle)
        throw new IllegalArgumentException("Angle must be a multiple of 90.")
    }
    rotatedPixels
  }

  /**
   * Rotates the image 90 degrees clockwise.
   *
   * @param item the input image to rotate
   * @return the rotated `Image[T]` with dimensions swapped
   */
  private def rotate90(item: Image[T]): Image[T] = {
    val width = item.getWidth
    val height = item.getHeight
    val rotated = Array.ofDim[T](width, height) // Dimensions are swapped

    for (y <- 0 until height; x <- 0 until width) {
      rotated(x)(height - y - 1) = item.getPixel(x, y)
    }

    new Image(rotated)
  }

  /**
   * Rotates the image 180 degrees clockwise.
   *
   * @param item the input image to rotate
   * @return the rotated `Image[T]`
   */
  private def rotate180(item: Image[T]): Image[T] = {
    val width = item.getWidth
    val height = item.getHeight
    val rotated = Array.ofDim[T](height, width)

    for (y <- 0 until height; x <- 0 until width) {
      rotated(height - y - 1)(width - x - 1) = item.getPixel(x, y)
    }

    new Image(rotated)
  }

  /**
   * Rotates the image 270 degrees clockwise.
   *
   * @param item the input image to rotate
   * @return the rotated `Image[T]` with dimensions swapped
   */
  private def rotate270(item: Image[T]): Image[T] = {
    val width = item.getWidth
    val height = item.getHeight
    val rotated = Array.ofDim[T](width, height) // Dimensions are swapped

    for (y <- 0 until height; x <- 0 until width) {
      rotated(width - x - 1)(y) = item.getPixel(x, y)
    }

    new Image(rotated)
  }

}
