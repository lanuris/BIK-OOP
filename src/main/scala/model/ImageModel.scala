package model

/**
 * A container class for managing multiple representations of an image.
 *
 * This class holds optional representations of an image:
 * - `imagePixel`: The original image with RGB pixel data.
 * - `imageGreyscale`: A greyscale representation of the image with intensity values.
 * - `imageChar`: An ASCII art representation of the image.
 *
 * Each representation is stored as an `Option`, allowing for flexibility in managing
 * the presence or absence of these image types.
 */
class ImageModel {
  var imagePixel: Option[Image[Pixel]] = None
  var imageGreyscale: Option[Image[Int]] = None
  var imageChar: Option[Image[Char]] = None
}
