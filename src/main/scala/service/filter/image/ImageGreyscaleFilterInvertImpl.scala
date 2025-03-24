package service.filter.image

import model.Image

/**
 * A concrete implementation of `ImageGreyscaleFilter` that inverts the grayscale values of an image.
 *
 * This class inverts each pixel's grayscale intensity, effectively creating a negative of the input image.
 * The grayscale values are assumed to be in the range of 0 to 255 (8-bit grayscale).
 */
class ImageGreyscaleFilterInvertImpl() extends ImageGreyscaleFilter {
  
  // The maximum grayscale value (assuming 8-bit grayscale image)
  private val White = 255

  /**
   * Applies the inversion filter to the given grayscale image.
   *
   * @param item the input grayscale image to be inverted
   * @return a new `Image[Int]` object with the inverted grayscale values
   * @throws IllegalArgumentException if the image has zero width or height
   */
  override def applyFilter(item: Image[Int]): Image[Int] = {

    // Ensure that the image has a valid size
    if (item.getHeight == 0 || item.getWidth == 0) throw new IllegalArgumentException("Image size error")

    invert(item)

  }

  /**
   * Inverts the grayscale values of each pixel in the given image.
   *
   * @param item the input grayscale image to invert
   * @return a new `Image[Int]` object with inverted grayscale pixel values
   */
  private def invert (item: Image[Int]): Image[Int] = {

    // Invert each pixel's grayscale value
    val invertedPixels = Array.ofDim[Int](item.getHeight, item.getWidth)

    for (x <- 0 until item.getWidth; y <- 0 until item.getHeight) {
      val originalPixel = item.getPixel(x, y)
      //need converter Ascii to new Ascii
      invertedPixels(y)(x) = White - originalPixel // Invert the pixel value
    }

    // Return a new image with the inverted pixels
    new Image(invertedPixels)
  }

}