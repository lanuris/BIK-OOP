package service.loader.image

import model.{Image, Pixel}
import service.loader.Loader

/**
 * A trait for generating random images as `Image[Pixel]` objects.
 *
 * This trait extends the `Loader` interface, specializing it for generating random image data
 * without relying on an external input source. The generated image is represented as an `Image[Pixel]`.
 */
trait ImageRandomGenerator extends Loader [Image[Pixel]]{

}
