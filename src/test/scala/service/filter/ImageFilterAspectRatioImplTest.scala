package service.filter

import org.scalatest.funsuite.AnyFunSuite
import model.Image
import service.filter.image.ImageFilterAspectRatioImpl

class ImageFilterAspectRatioImplTest extends AnyFunSuite {

  test("applyFilter with valid aspect ratio adjusts image height correctly") {
    val originalPixels = Array(
      Array('a', 'b', 'c'),
      Array('d', 'e', 'f'),
      Array('g', 'h', 'i')
    )
    val image = new Image[Char](originalPixels)
    val aspectRatioX = 2
    val aspectRatioY = 1
    val filter = new ImageFilterAspectRatioImpl[Char](aspectRatioX, aspectRatioY)
    val filteredImage = filter.applyFilter(image)

    // Calculate expected new height
    val scalingFactor = 2.0 / 1.0
    val expectedHeight = (image.getHeight * scalingFactor).toInt

    assert(filteredImage.getWidth == image.getWidth)
    assert(filteredImage.getHeight == expectedHeight)

    // Verify that pixels are correctly mapped
    for (y <- 0 until filteredImage.getHeight; x <- 0 until filteredImage.getWidth) {
      val originalY = Math.min((y / scalingFactor).toInt, image.getHeight - 1)
      assert(filteredImage.getPixel(x, y) == image.getPixel(x, originalY))
    }
  }

  test("applyFilter with aspect ratio '1:1' leaves image unchanged") {
    val originalPixels = Array(
      Array('x', 'y'),
      Array('z', 'w')
    )
    val image = new Image[Char](originalPixels)
    val aspectRatioX = 1
    val aspectRatioY = 1
    val filter = new ImageFilterAspectRatioImpl[Char](aspectRatioX, aspectRatioY)
    val filteredImage = filter.applyFilter(image)

    assert(filteredImage.getWidth == image.getWidth)
    assert(filteredImage.getHeight == image.getHeight)

    // Verify that pixels are the same
    for (y <- 0 until filteredImage.getHeight; x <- 0 until filteredImage.getWidth) {
      assert(filteredImage.getPixel(x, y) == image.getPixel(x, y))
    }
  }
  
  test("applyFilter throws exception for zero aspect ratio values") {
    val originalPixels = Array(
      Array('a')
    )
    val image = new Image[Char](originalPixels)
    val aspectRatioX = 0
    val aspectRatioY = 1
    val filter = new ImageFilterAspectRatioImpl[Char](aspectRatioX, aspectRatioY)
    
    val exception = intercept[Exception] {
      filter.applyFilter(image)
    }
    assert(exception.getMessage.contains("Invalid font aspect ratio"))
  }

  test("applyFilter throws exception for negative aspect ratio values") {
    val originalPixels = Array(
      Array('a')
    )
    val image = new Image[Char](originalPixels)
    val aspectRatioX = -1
    val aspectRatioY = 1
    val filter = new ImageFilterAspectRatioImpl[Char](aspectRatioX, aspectRatioY)

    val exception = intercept[Exception] {
      filter.applyFilter(image)
    }
    assert(exception.getMessage.contains("Invalid font aspect ratio"))
  }


  test("applyFilter on single-pixel image scales height correctly") {
    val originalPixels = Array(
      Array('a')
    )
    val image = new Image[Char](originalPixels)
    val aspectRatioX = 1
    val aspectRatioY = 2
    val filter = new ImageFilterAspectRatioImpl[Char](aspectRatioX, aspectRatioY)
    val filteredImage = filter.applyFilter(image)

    val scalingFactor = 1.0 / 2.0
    val expectedHeight = (image.getHeight * scalingFactor).toInt

    // Since original height is 1, expected height is 0 after scaling
    // However, minimum height should be at least 1 to contain the pixel
    val expectedHeightAdjusted = Math.max(expectedHeight, 1)

    assert(filteredImage.getWidth == image.getWidth)
    assert(filteredImage.getHeight == expectedHeightAdjusted)

    // Verify that pixels are correctly mapped
    for (y <- 0 until filteredImage.getHeight; x <- 0 until filteredImage.getWidth) {
      val originalY = Math.min((y / scalingFactor).toInt, image.getHeight - 1)
      assert(filteredImage.getPixel(x, y) == image.getPixel(x, originalY))
    }
  }

  test("applyFilter with aspect ratio resulting in zero height adjusts to minimum height") {
    val originalPixels = Array(
      Array('a', 'b'),
      Array('c', 'd')
    )
    val image = new Image[Char](originalPixels)
    val aspectRatioX = 1
    val aspectRatioY = 100
    val filter = new ImageFilterAspectRatioImpl[Char](aspectRatioX, aspectRatioY)
    val filteredImage = filter.applyFilter(image)

    // Expected height after scaling
    val scalingFactor = 1.0 / 100.0
    val expectedHeight = (image.getHeight * scalingFactor).toInt

    // Adjust expected height to at least 1
    val expectedHeightAdjusted = Math.max(expectedHeight, 1)

    assert(filteredImage.getWidth == image.getWidth)
    assert(filteredImage.getHeight == expectedHeightAdjusted)

    // Verify that pixels are correctly mapped
    for (y <- 0 until filteredImage.getHeight; x <- 0 until filteredImage.getWidth) {
      val originalY = Math.min((y / scalingFactor).toInt, image.getHeight - 1)
      assert(filteredImage.getPixel(x, y) == image.getPixel(x, originalY))
    }
  }
}
