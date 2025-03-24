package service.converter.image

import model.Image
import service.converter.Converter

/**
 * A specialized trait for converting images from one pixel data type to another.
 *
 * This trait extends the `Converter` interface, focusing on the conversion of 
 * `Image[T]` objects to `Image[R]` objects. It enables transformations where 
 * the pixel data type changes, such as from color to grayscale or applying specific 
 * processing to pixels.
 *
 * @tparam T the type of the input image's pixel data
 * @tparam R the type of the output image's pixel data
 */
trait ImageConverter[T, R] extends Converter [Image[T], Image[R]]{

}
