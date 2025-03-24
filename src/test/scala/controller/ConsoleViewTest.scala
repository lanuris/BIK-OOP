package controller

import org.scalatest.funsuite.AnyFunSuite
import org.mockito.Mockito._
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers._
import org.scalatestplus.mockito.MockitoSugar
import console.controller.Controller
import console.parser.Commands
import model.ImageModel
import console.view.ConsoleView

class ConsoleViewTest extends AnyFunSuite with MockitoSugar {

  // Mock dependencies
  val mockController: Controller = mock[Controller]
  val mockModel: ImageModel = mock[ImageModel]
  val consoleView = new ConsoleView(mockController)


  test("run should call showMainPage") {
    val args = Array("--image", "path/to/image.jpg")
    doNothing().when(mockController).showMainPage()

    consoleView.run(args, mockModel)

    verify(mockController).showMainPage()
  }
  
  test("run should process help command") {
    val args = Array("--help")
    doNothing().when(mockController).showHelp()

    consoleView.run(args, mockModel)

    verify(mockController).showHelp()
  }
  
}
