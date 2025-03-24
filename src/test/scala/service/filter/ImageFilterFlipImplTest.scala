package service.filter

import org.scalatest.funsuite.AnyFunSuite
import model.Image
import service.filter.image.ImageFilterFlipImpl
import org.mockito.Mockito._
import org.scalatestplus.mockito.MockitoSugar

class ImageFilterFlipImplTest extends AnyFunSuite with MockitoSugar {
  
  test("Flip along X axis only") {
    val originalPixels = Array(
      Array('a', 'b', 'c'),
      Array('d', 'e', 'f'),
      Array('g', 'h', 'i')
    )
    val image = new Image(originalPixels)
    val filter = new ImageFilterFlipImpl[Char](true, false)
    val resultImage = filter.applyFilter(image)
    val expectedPixels = Array(
      Array('g', 'h', 'i'),
      Array('d', 'e', 'f'),
      Array('a', 'b', 'c')
    )
    val expectedImage = new Image(expectedPixels)
    assert(resultImage.equals(expectedImage))
  }

  test("Flip along Y axis only") {
    val originalPixels = Array(
      Array('a', 'b', 'c'),
      Array('d', 'e', 'f'),
      Array('g', 'h', 'i')
    )
    val image = new Image(originalPixels)
    val filter = new ImageFilterFlipImpl[Char](false, true)
    val resultImage = filter.applyFilter(image)
    val expectedPixels = Array(
      Array('c', 'b', 'a'),
      Array('f', 'e', 'd'),
      Array('i', 'h', 'g')
    )
    val expectedImage = new Image(expectedPixels)
    assert(resultImage.equals(expectedImage))
  }

  test("Flip along both X and Y axes") {
    val originalPixels = Array(
      Array('a', 'b', 'c'),
      Array('d', 'e', 'f'),
      Array('g', 'h', 'i')
    )
    val image = new Image(originalPixels)
    val filter = new ImageFilterFlipImpl[Char](true, true)
    val resultImage = filter.applyFilter(image)
    val expectedPixels = Array(
      Array('i', 'h', 'g'),
      Array('f', 'e', 'd'),
      Array('c', 'b', 'a')
    )
    val expectedImage = new Image(expectedPixels)
    assert(resultImage.equals(expectedImage))
  }

  test("No flip (flipX and flipY are false)") {
    val originalPixels = Array(
      Array('a', 'b', 'c'),
      Array('d', 'e', 'f'),
      Array('g', 'h', 'i')
    )
    val image = new Image(originalPixels)
    val filter = new ImageFilterFlipImpl[Char](false, false)
    val resultImage = filter.applyFilter(image)
    val expectedImage = new Image(originalPixels)
    assert(resultImage.equals(expectedImage))
  }

  test("Image with zero height throws exception") {
    val mockImage = mock[Image[Char]]
    when(mockImage.getHeight).thenReturn(0)
    val filter = new ImageFilterFlipImpl[Char](flipX = true, flipY = true)
    val exception = intercept[Exception] {
      filter.applyFilter(mockImage)
    }
    assert(exception.getMessage.contains("Image size error"))
  }

  test("Image with zero width throws exception") {
    val mockImage = mock[Image[Char]]
    when(mockImage.getWidth).thenReturn(0)
    val filter = new ImageFilterFlipImpl[Char](flipX = true, flipY = true)
    val exception = intercept[Exception] {
      filter.applyFilter(mockImage)
    }
    assert(exception.getMessage.contains("Image size error"))
  }

  test("Flip a non-square image along X axis") {
    val originalPixels = Array(
      Array('a', 'b'),
      Array('c', 'd'),
      Array('e', 'f')
    )
    val image = new Image(originalPixels)
    val filter = new ImageFilterFlipImpl[Char](true, false)
    val resultImage = filter.applyFilter(image)
    val expectedPixels = Array(
      Array('e', 'f'),
      Array('c', 'd'),
      Array('a', 'b')
    )
    val expectedImage = new Image(expectedPixels)
    assert(resultImage.equals(expectedImage))
  }

  test("Flip an image with height 1 in X along Y axis") {
    val originalPixels = Array(
      Array('a', 'b', 'c')
    ) // Only one row
    val image = new Image(originalPixels)
    val filter = new ImageFilterFlipImpl[Char](false, true)
    val resultImage = filter.applyFilter(image)
    val expectedPixels = Array(
      Array('c', 'b', 'a')
    )
    val expectedImage = new Image(expectedPixels)
    assert(resultImage.equals(expectedImage))
  }

  test("Flip an image with height 1 in X along X axis") {
    val originalPixels = Array(
      Array('a', 'b', 'c')
    ) // Only one row
    val image = new Image(originalPixels)
    val filter = new ImageFilterFlipImpl[Char](true, false)
    val resultImage = filter.applyFilter(image)
    val expectedImage = new Image(originalPixels)
    assert(resultImage.equals(expectedImage))
  }

  test("Flip an image with width 1 in Y along X axis") {
    val originalPixels = Array(
      Array('a'),
      Array('b'),
      Array('c')
    ) // Only one column
    val image = new Image(originalPixels)
    val filter = new ImageFilterFlipImpl[Char](true, false)
    val resultImage = filter.applyFilter(image)
    val expectedPixels = Array(
      Array('c'),
      Array('b'),
      Array('a')
    )
    val expectedImage = new Image(expectedPixels)
    assert(resultImage.equals(expectedImage))
  }

  test("Flip an image with width 1 in Y along Y axis") {
    val originalPixels = Array(
      Array('a'),
      Array('b'),
      Array('c')
    ) // Only one column
    val image = new Image(originalPixels)
    val filter = new ImageFilterFlipImpl[Char](false, true)
    val resultImage = filter.applyFilter(image)
    val expectedImage = new Image(originalPixels)
    assert(resultImage.equals(expectedImage))
  }

}
