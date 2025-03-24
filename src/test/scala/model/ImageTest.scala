package model

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers
import model.visitor.ImageVisitor

class ImageTest extends AnyFunSuite with Matchers {

  test("Image dimensions are computed correctly") {
    val pixels = Array(Array(1, 2), Array(3, 4))
    val image = new Image[Int](pixels)
    image.getWidth shouldEqual 2
    image.getHeight shouldEqual 2
  }

  test("getPixel returns correct value for valid indices") {
    val pixels = Array(Array(1, 2), Array(3, 4))
    val image = new Image[Int](pixels)
    image.getPixel(0, 0) shouldEqual 1
    image.getPixel(1, 1) shouldEqual 4
  }

  test("getPixel throws IndexOutOfBoundsException for invalid indices") {
    val pixels = Array(Array(1, 2), Array(3, 4))
    val image = new Image[Int](pixels)
    intercept[IndexOutOfBoundsException] {
      image.getPixel(-1, 0)
    }
    intercept[IndexOutOfBoundsException] {
      image.getPixel(2, 0)
    }
    intercept[IndexOutOfBoundsException] {
      image.getPixel(0, 2)
    }
  }

  test("equals correctly compares two images") {
    val pixels1 = Array(Array(1, 2), Array(3, 4))
    val pixels2 = Array(Array(1, 2), Array(3, 4))
    val pixels3 = Array(Array(5, 6), Array(7, 8))

    val image1 = new Image[Int](pixels1)
    val image2 = new Image[Int](pixels2)
    val image3 = new Image[Int](pixels3)

    image1.equals(image2) shouldBe true
    image1.equals(image3) shouldBe false
  }

  test("pixelIterator iterates over all pixels correctly") {
    val pixels = Array(Array(1, 2), Array(3, 4))
    val image = new Image[Int](pixels)

    val iterator = image.pixelIterator.toList
    iterator should contain theSameElementsAs List(
      ((0, 0), 1), ((1, 0), 2),
      ((0, 1), 3), ((1, 1), 4)
    )
  }

  test("accept method interacts with visitor correctly") {
    // Mock visitor behavior
    class MockImageVisitor extends ImageVisitor[Int, String] {
      override def visitImage(image: Image[Int]): String = "Visited"
    }

    val pixels = Array(Array(1, 2), Array(3, 4))
    val image = new Image[Int](pixels)
    val visitor = new MockImageVisitor

    image.accept(visitor) shouldEqual "Visited"
  }
}
