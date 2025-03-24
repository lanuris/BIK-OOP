package service.filter.image

import model.Image
import scala.reflect.ClassTag

/**
 * A concrete implementation of `ImageFilter` that adjusts an image's aspect ratio.
 *
 * This class rescales the height of an image to match the specified aspect ratio while maintaining
 * the original width. The aspect ratio is defined by `aspectWidth` and `aspectHeight`. The scaling
 * process uses nearest-neighbor interpolation to ensure the resized image maintains its visual integrity.
 *
 * @param aspectWidth the width component of the desired aspect ratio
 * @param aspectHeight the height component of the desired aspect ratio
 * @tparam T the type of the pixel data in the image
 */
class ImageFilterAspectRatioImpl [T: ClassTag] (aspectWidth: Int, aspectHeight: Int) extends ImageFilter [T] {

  /**
   * Applies the aspect ratio adjustment filter to the given image.
   *
   * @param item the input image to be adjusted
   * @return a new `Image[T]` object with the adjusted aspect ratio
   * @throws IllegalArgumentException if the image has zero width or height
   * @throws Exception                if the specified aspect ratio is invalid
   */
  override def applyFilter(item: Image[T]): Image[T] = {
    
    if (item.getHeight == 0 || item.getWidth == 0) throw new IllegalArgumentException("Image size error")

    if (aspectWidth <= 0 || aspectHeight <= 0)
      throw new Exception("Invalid font aspect ratio")

    scale(item)
    
  }

  /**
   * Scales the image to match the specified aspect ratio while preserving the original width.
   *
   * @param item the input image to be scaled
   * @return a new `Image[T]` object with adjusted height to match the aspect ratio
   */
  private def scale (item: Image[T]) : Image[T] = {

    val currentWidth = item.getWidth
    val currentHeight = item.getHeight

    // Calculate the scaling factor to adjust the image's aspect ratio
    val scalingFactor = aspectWidth.toDouble / aspectHeight
    val newHeight = Math.max((currentHeight * scalingFactor).toInt, 1) // Ensure at least 1

    // Create a new image with the adjusted height and the original width
    val adjustedPixels = Array.ofDim[T](newHeight, currentWidth)

    // Rescale the pixels using nearest-neighbor interpolation
    for (y <- 0 until newHeight; x <- 0 until currentWidth) {
      val originalY = Math.min((y / scalingFactor).toInt, currentHeight - 1)
      adjustedPixels(y)(x) = item.getPixel(x, originalY)
    }

    new Image(adjustedPixels)
  }

}
