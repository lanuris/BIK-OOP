package model

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

class PixelTest extends AnyFunSuite with Matchers {

  test("Pixel equality: same RGB values are equal") {
    val pixel1 = new Pixel(100, 150, 200)
    val pixel2 = new Pixel(100, 150, 200)

    pixel1 shouldEqual pixel2
  }

  test("Pixel equality: different RGB values are not equal") {
    val pixel1 = new Pixel(100, 150, 200)
    val pixel2 = new Pixel(200, 150, 100)

    pixel1 should not equal pixel2
  }

  test("Pixel equality: not equal to other types") {
    val pixel = new Pixel(100, 150, 200)
    val notAPixel = "Not a Pixel"

    pixel should not equal notAPixel
  }

  test("Pixel hashCode: same RGB values have the same hash code") {
    val pixel1 = new Pixel(100, 150, 200)
    val pixel2 = new Pixel(100, 150, 200)

    pixel1.hashCode() shouldEqual pixel2.hashCode()
  }

  test("Pixel hashCode: different RGB values have different hash codes") {
    val pixel1 = new Pixel(100, 150, 200)
    val pixel2 = new Pixel(200, 150, 100)

    pixel1.hashCode() should not equal pixel2.hashCode()
  }

  test("Pixel toString: correctly represents pixel") {
    val pixel = new Pixel(100, 150, 200)

    pixel.toString shouldEqual "Pixel(100, 150, 200)"
  }

}

