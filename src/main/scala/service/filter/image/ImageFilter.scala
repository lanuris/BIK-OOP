package service.filter.image

import model.Image
import service.filter.Filter

/**
 * A trait for applying filters to an `Image` object.
 *
 * This trait extends the `Filter` interface, specializing it for filtering operations on images.
 * It takes an `Image[T]` as input and returns the filtered `Image[T]` as output. The type parameter `T`
 * represents the data type of each pixel in the image (e.g., `Pixel` or other custom data structures).
 *
 * @tparam T the type of the pixel data in the image
 */
trait ImageFilter[T] extends Filter[Image[T]]{

}
