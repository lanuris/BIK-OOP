package service.loader

import org.scalatest.funsuite.AnyFunSuite
import org.scalatestplus.mockito.MockitoSugar
import org.mockito.Mockito.*
import org.mockito.ArgumentMatchers.*

import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO
import service.loader.image.{ImageDataExtractor, ImageFileLoaderAbs, ImageFileLoaderPngImpl, ImageFileLoaderJpgImpl}
import model.{Image, Pixel}

class ImageFileLoaderGeneralTest extends AnyFunSuite with MockitoSugar {

  val pngLoader = new ImageFileLoaderPngImpl
  val jpgLoader = new ImageFileLoaderJpgImpl
  runTestsFor(pngLoader, "png", "jpg")
  runTestsFor(jpgLoader, "jpg", "png")


    def runTestsFor(loader: ImageFileLoaderAbs, supportedFormat: String, unsupportedFormat: String): Unit = {

      test(s"${loader.getClass.getSimpleName} - load should load a valid image file with an absolute path") {

        // Arrange
        // Create a temporary directory for testing
        val tempDir = System.getProperty("java.io.tmpdir")
        val absolutePath = s"$tempDir/valid_image_absolute.$supportedFormat"
        val imageFile = new File(absolutePath)

        // Create a valid image file
        val bufferedImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB)
        ImageIO.write(bufferedImage, supportedFormat, imageFile)

        // Act
        val loadedImage = loader.load(absolutePath)

        // Assert
        assert(loadedImage != null, "Expected loaded image to not be null")
        assert(loadedImage.getWidth == 100 && loadedImage.getHeight == 100, "Expected image to have width 100 and height 100")

        // Cleanup
        imageFile.delete()
      }

      test(s"${loader.getClass.getSimpleName} - load should load a valid image file with a relative path") {
        // Arrange
        val relativeDir = new File("test_images")
        val relativePath = s"test_images/valid_image_relative.$supportedFormat"
        val imageFile = new File(relativePath)

        // Ensure the directory exists
        if (!relativeDir.exists()) relativeDir.mkdir()

        // Create a valid image file
        val bufferedImage = new BufferedImage(200, 200, BufferedImage.TYPE_INT_RGB)
        ImageIO.write(bufferedImage, supportedFormat, imageFile)

        // Act
        val loadedImage = loader.load(relativePath)

        // Assert
        assert(loadedImage != null, "Expected loaded image to not be null")
        assert(loadedImage.getWidth == 200 && loadedImage.getHeight == 200, "Expected image to have width 200 and height 200")

        // Cleanup
        imageFile.delete()
        relativeDir.delete()
      }

      test(s"${loader.getClass.getSimpleName} - load should throw exception for nonexistent file") {
        val exception = intercept[IllegalArgumentException] {
          loader.load(s"nonexistent.$supportedFormat")
        }
        assert(exception.getMessage.contains("Image file not found"))
      }

      test(s"${loader.getClass.getSimpleName} - load should throw exception for unsupported format") {
        val unsupportedFile = new File(s"test_image.$unsupportedFormat")
        unsupportedFile.deleteOnExit()

        val bufferedImage = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB)
        ImageIO.write(bufferedImage, unsupportedFormat, unsupportedFile)

        val exception = intercept[IllegalArgumentException] {
          loader.load(unsupportedFile.getPath)
        }
        assert(exception.getMessage.contains("Invalid file format"))
      }

      test(s"${loader.getClass.getSimpleName} - load should throw exception on image loading error") {
        val invalidFile = File.createTempFile("invalid", s".$supportedFormat")
        invalidFile.deleteOnExit()

        val exception = intercept[Exception] {
          loader.load(invalidFile.getAbsolutePath)
        }
        assert(exception.getMessage.contains("Image loading error"))
      }

      test(s"${loader.getClass.getSimpleName} - load should throw exception for zero-width") {
        val supportedFile = new File(s"zero_width.$supportedFormat")
        ImageIO.write(new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB), supportedFormat, supportedFile)
        supportedFile.deleteOnExit()

        val mockBufferedImage = mock[BufferedImage]
        when(mockBufferedImage.getWidth).thenReturn(0)
        when(mockBufferedImage.getHeight).thenReturn(100)

        val fileMock = mockStatic(classOf[ImageIO])
        fileMock.when(() => ImageIO.read(any[File])).thenReturn(mockBufferedImage)

        val exception = intercept[Exception] {
          loader.load(supportedFile.getPath)
        }
        assert(exception.getMessage.contains("Image size error"))

        fileMock.close()
      }

      test(s"${loader.getClass.getSimpleName} - load should throw exception for zero-height") {
        val supportedFile = new File(s"zero_height.$supportedFormat")
        ImageIO.write(new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB), supportedFormat, supportedFile)
        supportedFile.deleteOnExit()

        val mockBufferedImage = mock[BufferedImage]
        when(mockBufferedImage.getWidth).thenReturn(100)
        when(mockBufferedImage.getHeight).thenReturn(0)

        val fileMock = mockStatic(classOf[ImageIO])
        fileMock.when(() => ImageIO.read(any[File])).thenReturn(mockBufferedImage)

        val exception = intercept[Exception] {
          loader.load(supportedFile.getPath)
        }
        assert(exception.getMessage.contains("Image size error"))

        fileMock.close()
      }

    }
}

