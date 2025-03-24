package console.view.rendering

import model.Image
import model.visitor.ImageVisitor


/**
 * A concrete implementation of the `ImageVisitor` pattern for rendering an image as a string.
 *
 * The `ImageRenderingVisitor` class traverses the pixels of an image and generates a string
 * representation. Each row of pixels corresponds to a line in the output string, allowing
 * the rendered image to be displayed in a textual format, such as ASCII art.
 *
 * The Visitor pattern is a behavioral design pattern used in object-oriented programming
 * to separate an algorithm from the object structure on which it operates.
 *
 * This implementation uses the **Visitor pattern**, which separates the rendering algorithm
 * from the image structure, promoting the open/closed principle. This allows the rendering
 * logic to be extended without modifying the `Image` class.
 *
 * @tparam T the type of the pixel data in the image (e.g., `Char`, `Int`, `Pixel`)
 */
class ImageRenderingVisitor[T] extends ImageVisitor[T, String]{

  /**
   * Visits the given image and generates its string representation.
   *
   * Each row of pixels in the image is rendered as a line in the output string.
   * The rows are separated by newline characters (`\n`).
   *
   * @param image the `Image[T]` to render
   * @return the string representation of the image
   */
  override def visitImage(image: Image[T]): String = {
    val sb = new StringBuilder
    for (y <- 0 until image.getHeight) {
      for (x <- 0 until image.getWidth) {
        sb.append(image.getPixel(x, y))
      }
      sb.append("\n")
    }
    sb.toString()
  }  
}
