package service.loader.image

import service.loader.DataExtractor
import model.{Image, Pixel}
import java.awt.image.BufferedImage

/**
 * A trait for extracting image data from a `BufferedImage` and converting it into an `Image[Pixel]`.
 *
 * This trait extends the `DataExtractor` interface, specializing it for image processing
 * tasks where the input is a `BufferedImage` and the output is an `Image` object containing `Pixel` data.
 */
trait ImageDataExtractor extends DataExtractor [BufferedImage, Image[Pixel]] {
  
}
