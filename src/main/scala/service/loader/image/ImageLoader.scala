package service.loader.image

import service.loader.Loader
import model.{Image, Pixel}

/**
 * A trait for loading image data and converting it into an `Image[Pixel]` object.
 *
 * This trait extends the `Loader` interface, specializing it for tasks that involve
 * loading image files or data from a specified input of type `T` and returning an
 * `Image[Pixel]` representation.
 *
 * @tparam T the type of the input source (e.g., file path, URL, or other data reference)
 */
trait ImageLoader[T] extends Loader [Image[Pixel]]{

  /**
   * Loads an image from the specified input source and returns it as an `Image[Pixel]`.
   *
   * @param path the input source of type `T` to load the image from
   * @return the loaded `Image[Pixel]` object
   */
  def load(path: T): Image[Pixel]

}
