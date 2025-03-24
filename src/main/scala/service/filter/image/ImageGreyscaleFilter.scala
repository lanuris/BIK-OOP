package service.filter.image

/**
 * A trait for applying a greyscale filter to an image.
 *
 * This trait extends the `ImageFilter` interface, specializing it for images where pixel data is
 * represented as integers (e.g., grayscale intensity values). Implementations of this trait
 * should provide the logic to convert an image to its greyscale representation.
 */
trait ImageGreyscaleFilter extends ImageFilter [Int]{

}
