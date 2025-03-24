package service.converter.image

import model.Pixel

/**
 * A specialized trait for converting images with color pixel data to greyscale.
 *
 * This trait extends `ImageConverter`, converting an `Image[Pixel]` with color pixel data 
 * (e.g., RGB) to an `Image[Int]` where each pixel represents a greyscale intensity value.
 * The greyscale value is typically derived from the RGB components of the input pixel.
 */
trait ImageConverterGreyscale extends ImageConverter [Pixel, Int]{

}
