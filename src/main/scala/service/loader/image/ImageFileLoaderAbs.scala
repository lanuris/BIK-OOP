package service.loader.image

import model.{Image, Pixel}
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

/**
 * An abstract class for loading image files into an `Image[Pixel]` representation.
 *
 * This class extends the `ImageFileLoader` trait, providing shared functionality for handling
 * image file validation, reading, and ensuring the file format matches the expected type.
 * It defines a `supportedFormat` field for specifying the file format (e.g., "png", "jpg")
 * and utility methods for image file operations.
 */
abstract class ImageFileLoaderAbs extends ImageFileLoader {

  /**
   * Specifies the supported file format for this loader.
   */
  protected val supportedFormat: String

  /**
   * Throws a `NotImplementedError` to enforce the usage of `load(path: String)` instead of `load()`.
   *
   * @return nothing, always throws an error
   */
  override def load(): Image[Pixel] = throw new NotImplementedError("Use load(path: String) instead.")

  /**
   * Validates the given image file to ensure it matches the supported format.
   *
   * @param file the image file to validate
   * @return `true` if the file format matches the `supportedFormat`, `false` otherwise
   */
  protected def validate(file: File): Boolean = {
    val fileName = file.getName.toLowerCase
    fileName.endsWith(supportedFormat)
  }

  /**
   * Reads the image file into a `BufferedImage`.
   *
   * Ensures the file exists, matches the expected format, and contains valid image data.
   *
   * @param file the image file to read
   * @return the loaded `BufferedImage` object
   * @throws IllegalArgumentException if the file does not exist or has an invalid format
   * @throws Exception                if an error occurs during image loading or if the image is invalid
   */
  protected def readImage(file: File): BufferedImage = {
    if (!file.exists()) throw new IllegalArgumentException("Image file not found")
    if (!validate(file)) throw new IllegalArgumentException(s"Invalid file format, expected $supportedFormat")

    val bufferedImage: BufferedImage = try {
      ImageIO.read(file)
    } catch {
      case _: Exception => throw new Exception("Image loading error")
    }

    if (bufferedImage == null) throw new IllegalArgumentException("Image loading error")
    if (bufferedImage.getWidth == 0 || bufferedImage.getHeight == 0) throw new Exception("Image size error")

    bufferedImage
  }
}
