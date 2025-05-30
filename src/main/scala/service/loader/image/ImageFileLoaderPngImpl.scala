package service.loader.image

import model.{Image, Pixel}
import java.io.File

/**
 * A concrete implementation of `ImageFileLoaderAbs` for loading PNG images.
 *
 * This class specializes the `ImageFileLoaderAbs` to handle PNG image files. It uses an
 * `ImageDataExtractor` to process the loaded `BufferedImage` into an `Image[Pixel]`
 * representation and optionally provides a fallback `ImageRandomGenerator` for generating
 * random images if needed.
 *
 * @param imageDataExtractor   an instance of `ImageDataExtractor` for converting `BufferedImage`
 *                             to `Image[Pixel]`. Defaults to `ImageDataExtractorRgbImpl`.
 * @param randomImageGenerator an instance of `ImageRandomGenerator` for generating random images.
 *                             Defaults to `ImageRandomGeneratorImpl`.
 */
class ImageFileLoaderPngImpl (imageDataExtractor: ImageDataExtractor = new ImageDataExtractorRgbImpl,
                              randomImageGenerator: ImageRandomGenerator = new ImageRandomGeneratorImpl)
                              extends ImageFileLoaderAbs {
  /**
   * Specifies the supported file format for this loader as "png".
   */
  override protected val supportedFormat: String = "png"

  /**
   * Loads a PNG image from the specified file path and processes it into an `Image[Pixel]`.
   *
   * @param path the file path of the PNG image to load
   * @return the loaded `Image[Pixel]` representation
   * @throws IllegalArgumentException if the file is not found or the format is invalid
   * @throws Exception                if any error occurs during image loading or processing
   */
  override def load (path: String): Image[Pixel] = {
    val file = new File(path)
    val bufferedImage = readImage(file)
    imageDataExtractor.extract(bufferedImage)
  }

}

