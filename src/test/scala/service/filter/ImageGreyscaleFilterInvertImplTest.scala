package service.filter

import org.scalatest.funsuite.AnyFunSuite
import model.Image
import service.filter.image.ImageGreyscaleFilterInvertImpl

class ImageGreyscaleFilterInvertImplTest extends AnyFunSuite {

  test("Invert filter should correctly invert a single pixel image") {
    val originalPixels = Array(Array(100))
    val image = new Image(originalPixels)
    val filter = new ImageGreyscaleFilterInvertImpl()
    val resultImage = filter.applyFilter(image)

    assert(resultImage.getPixel(0, 0) == 155) // 255 - 100 = 155
  }

  test("Invert filter should correctly invert a small image") {
    val originalPixels = Array(
      Array(0, 64),
      Array(128, 192)
    )
    val image = new Image(originalPixels)
    val filter = new ImageGreyscaleFilterInvertImpl()
    val resultImage = filter.applyFilter(image)

    val expectedPixels = Array(
      Array(255, 191),
      Array(127, 63)
    )

    for (x <- 0 until image.getWidth; y <- 0 until image.getHeight) {
      assert(resultImage.getPixel(x, y) == expectedPixels(y)(x))
    }
  }

  test("Invert filter should invert an image with all zeros (black)") {
    val originalPixels = Array.fill(2, 2)(0)
    val image = new Image(originalPixels)
    val filter = new ImageGreyscaleFilterInvertImpl()
    val resultImage = filter.applyFilter(image)

    for (x <- 0 until image.getWidth; y <- 0 until image.getHeight) {
      assert(resultImage.getPixel(x, y) == 255)
    }
  }

  test("Invert filter should invert an image with all 255s (white)") {
    val originalPixels = Array.fill(2, 2)(255)
    val image = new Image(originalPixels)
    val filter = new ImageGreyscaleFilterInvertImpl()
    val resultImage = filter.applyFilter(image)

    for (x <- 0 until image.getWidth; y <- 0 until image.getHeight) {
      assert(resultImage.getPixel(x, y) == 0)
    }
  }

  test("Invert filter should handle an empty image and throw an exception") {
    val originalPixels = Array.ofDim[Int](0, 0)
    val image = new Image(originalPixels)
    val filter = new ImageGreyscaleFilterInvertImpl()

    assertThrows[IllegalArgumentException] {
      filter.applyFilter(image)
    }
  }

  test("Invert filter should correctly invert a large image") {
    val originalPixels = Array.tabulate(10, 10)((x, y) => x * y % 256)
    val image = new Image(originalPixels)
    val filter = new ImageGreyscaleFilterInvertImpl()
    val resultImage = filter.applyFilter(image)

    for (x <- 0 until image.getWidth; y <- 0 until image.getHeight) {
      val expectedValue = 255 - originalPixels(y)(x)
      assert(resultImage.getPixel(x, y) == expectedValue)
    }
  }
}
