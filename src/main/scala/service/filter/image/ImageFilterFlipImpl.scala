package service.filter.image

import model.Image
import scala.reflect.ClassTag

/**
 * A concrete implementation of `ImageFilter` that flips an image horizontally, vertically, or both.
 *
 * This class provides functionality to flip an image based on the specified axes.
 * The `flipX` parameter determines whether the image is flipped vertically, and the `flipY`
 * parameter determines whether the image is flipped horizontally.
 *
 * @param flipX a boolean flag indicating whether to flip the image vertically
 * @param flipY a boolean flag indicating whether to flip the image horizontally
 * @tparam T the type of the pixel data in the image
 */
class ImageFilterFlipImpl [T: ClassTag] (flipX: Boolean, flipY: Boolean) extends ImageFilter [T] {

  /**
   * Applies the flip filter to the given image.
   *
   * @param item the input image to be flipped
   * @return a new `Image[T]` object with the specified flip transformations applied
   * @throws IllegalArgumentException if the image has zero width or height
   */
  override def applyFilter(item: Image[T]): Image[T] = {
    if (item.getHeight == 0 || item.getWidth == 0)
      throw new IllegalArgumentException("Image size error")
    
    flip(item, flipX, flipY)
  }

  /**
   * Flips the image along the specified axes.
   *
   * @param item  the input image to be flipped
   * @param flipX whether to flip the image vertically
   * @param flipY whether to flip the image horizontally
   * @return a new `Image[T]` object with the flipped pixel data
   */
  private def flip(item: Image[T], flipX: Boolean, flipY: Boolean): Image[T] = {
    val width = item.getWidth
    val height = item.getHeight
    val flipped = Array.ofDim[T](height, width)

    for (x <- 0 until width; y <- 0 until height) {
      val newX = if (flipY) width - 1 - x else x
      val newY = if (flipX) height - 1 - y else y
      flipped(newY)(newX) = item.getPixel(x, y)
    }

    new Image(flipped)
  } 
}  
