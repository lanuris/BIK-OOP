package service.filter

import model.Image
import org.scalatest.funsuite.AnyFunSuite
import service.filter.image.ImageFilterRotateImpl

class ImageFilterRotateImplTest extends AnyFunSuite {
  
  test("Rotate by 0 degrees") {
    val originalPixels = Array(
      Array('A', 'B', 'C'),
      Array('D', 'E', 'F'),
      Array('G', 'H', 'I')
    )
    val originalImage = new Image(originalPixels)
    val rotateFilter = new ImageFilterRotateImpl[Char](0)
    val rotatedImage = rotateFilter.applyFilter(originalImage)
    assert(rotatedImage.equals(originalImage))
  }

  test("Rotate by 90 degrees") {
    val originalPixels = Array(
      Array('A', 'B'),
      Array('C', 'D'),
      Array('E', 'F')
    )
    val expectedPixels = Array(
      Array('E', 'C', 'A'),
      Array('F', 'D', 'B')
    )
    val originalImage = new Image(originalPixels)
    val expectedImage = new Image(expectedPixels)
    val rotateFilter = new ImageFilterRotateImpl[Char](90)
    val rotatedImage = rotateFilter.applyFilter(originalImage)
    assert(rotatedImage.equals(expectedImage))
  }

  test("Rotate by 180 degrees") {
    val originalPixels = Array(
      Array('A', 'B'),
      Array('C', 'D'),
      Array('E', 'F')
    )
    val expectedPixels = Array(
      Array('F', 'E'),
      Array('D', 'C'),
      Array('B', 'A')
    )
    val originalImage = new Image(originalPixels)
    val expectedImage = new Image(expectedPixels)
    val rotateFilter = new ImageFilterRotateImpl[Char](180)
    val rotatedImage = rotateFilter.applyFilter(originalImage)
    assert(rotatedImage.equals(expectedImage))
  }

  test("Rotate by 270 degrees") {
    val originalPixels = Array(
      Array('A', 'B'),
      Array('C', 'D'),
      Array('E', 'F')
    )
    val expectedPixels = Array(
      Array('B', 'D', 'F'),
      Array('A', 'C', 'E')
    )
    val originalImage = new Image(originalPixels)
    val expectedImage = new Image(expectedPixels)
    val rotateFilter = new ImageFilterRotateImpl[Char](270)
    val rotatedImage = rotateFilter.applyFilter(originalImage)
    assert(rotatedImage.equals(expectedImage))
  }

  test("Rotate by negative angle (e.g., -90 degrees)") {
    val originalPixels = Array(
      Array('A', 'B', 'C'),
      Array('D', 'E', 'F'),
      Array('G', 'H', 'I')
    )
    val expectedPixels = Array(
      Array('C', 'F', 'I'),
      Array('B', 'E', 'H'),
      Array('A', 'D', 'G')
    )
    val originalImage = new Image(originalPixels)
    val expectedImage = new Image(expectedPixels)
    val rotateFilter = new ImageFilterRotateImpl[Char](-90)
    val rotatedImage = rotateFilter.applyFilter(originalImage)
    assert(rotatedImage.equals(expectedImage))
  }

  test("Rotate with angle not multiple of 90 should throw exception") {
    val originalPixels = Array(
      Array('A', 'B'),
      Array('C', 'D')
    )
    val originalImage = new Image(originalPixels)
    val rotateFilter = new ImageFilterRotateImpl[Char](45)
    val exception = intercept[IllegalArgumentException] {
      rotateFilter.applyFilter(originalImage)
    }
    assert(exception.getMessage.contains("Angle must be a multiple of 90."))
  }

  test("Rotate empty image should throw exception") {
    val emptyPixels = Array.ofDim[Char](0, 0)
    val emptyImage = new Image(emptyPixels)
    val rotateFilter = new ImageFilterRotateImpl[Char](90)
    val exception = intercept[IllegalArgumentException] {
      rotateFilter.applyFilter(emptyImage)
    }
    assert(exception.getMessage.contains("Image size error"))
  }

  test("Rotate non-square image by 90 degrees") {
    val originalPixels = Array(
      Array('A', 'B', 'C', 'D'),
      Array('E', 'F', 'G', 'H')
    )
    val expectedPixels = Array(
      Array('E', 'A'),
      Array('F', 'B'),
      Array('G', 'C'),
      Array('H', 'D')
    )
    val originalImage = new Image(originalPixels)
    val expectedImage = new Image(expectedPixels)
    val rotateFilter = new ImageFilterRotateImpl[Char](90)
    val rotatedImage = rotateFilter.applyFilter(originalImage)
    assert(rotatedImage.equals(expectedImage))
  }
}  
