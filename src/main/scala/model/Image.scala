package model

import model.visitor.ImageVisitor

/**
 * A generic representation of an image with pixel data of type `T`.
 *
 * This class encapsulates a 2D array of pixels and provides methods to access image properties,
 * retrieve pixel data, iterate through pixels, and support the visitor design pattern for
 * image-related operations.
 *
 * @param pixels a 2D array representing the image's pixel data
 * @tparam T the type of the pixel data
 */
class Image[T] (val pixels: Array[Array[T]]) {

  /**
   * Gets the width of the image (number of columns in the pixel array).
   *
   * @return the width of the image
   */
  def getWidth: Int = {
    pixels(0).length
  }

  /**
   * Gets the height of the image (number of rows in the pixel array).
   *
   * @return the height of the image
   */
  def getHeight: Int = {
    pixels.length
  }

  /**
   * Retrieves the pixel value at the specified (x, y) coordinates.
   *
   * @param x the x-coordinate of the pixel
   * @param y the y-coordinate of the pixel
   * @return the pixel value of type `T` at the given coordinates
   * @throws IndexOutOfBoundsException if the coordinates are outside the image bounds
   */
  def getPixel(x: Int, y: Int): T = {
    if (x >= 0 && x < getWidth && y >= 0 && y < getHeight)
      pixels(y)(x)
    else
      throw new IndexOutOfBoundsException(s"Invalid pixel coordinates: ($x, $y)")  
  }

  /**
   * Provides an iterator for traversing the image's pixels along with their coordinates.
   *
   * @return an iterator of tuples containing pixel coordinates and their values
   */
  def pixelIterator: Iterator[((Int, Int), T)] = {
    for {
      x <- (0 until getWidth).iterator
      y <- (0 until getHeight).iterator
    } yield ((x, y), getPixel(x, y))
  }

  /**
   * Compares this image with another image for equality.
   *
   * @param other the image to compare with this image
   * @return true if the images are equal (same dimensions and pixel values), false otherwise
   */
  def equals(other: Image[T]): Boolean = {
    if (this.getWidth != other.getWidth || this.getHeight != other.getHeight) {
      false
    } else {
      this.pixelIterator.zip(other.pixelIterator).forall {
        case (((x1, y1), pixel1), ((x2, y2), pixel2)) =>
          x1 == x2 && y1 == y2 && pixel1 == pixel2
      }
    }
  }

  /**
   * Accepts a joke visitor
   *
   * @param visitor the visitor
   * @tparam R the return type of the visitor
   * @return the result of the visitor's operation
   */
  def accept[R](visitor: ImageVisitor[T,R]): R = {
    visitor.visitImage(this)
  }
  
}
