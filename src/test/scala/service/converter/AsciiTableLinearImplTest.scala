package service.converter

import service.converter.table.AsciiTableLinearImpl
import org.scalatest.funsuite.AnyFunSuite

class AsciiTableLinearImplTest extends AnyFunSuite {

  // Sample characters for testing, e.g., simple greyscale to ASCII mapping
  val characters = "@%#*+=-:. "
  val asciiTable = new AsciiTableLinearImpl(characters)

  test("getAsciiForGreyscale should return the first character for 0 greyscale") {
    assert(asciiTable.getAsciiForGreyscale(0) == characters.head)
  }

  test("getAsciiForGreyscale should return the last character for 255 greyscale") {
    assert(asciiTable.getAsciiForGreyscale(255) == characters.last)
  }

  test("getAsciiForGreyscale should return the middle character for middle greyscale value") {
    val middleGreyscale = 127
    val expectedCharacter = characters(middleGreyscale / (255 / (characters.length)))
    assert(asciiTable.getAsciiForGreyscale(middleGreyscale) == expectedCharacter)
  }
  
}

