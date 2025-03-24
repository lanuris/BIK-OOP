package console.view.page.concrete

import console.view.page.TextPage
import console.view.rendering.ImageRenderingVisitor
import model.Image

/**
 * A concrete implementation of `TextPage` for rendering an image.
 *
 * The `ImagePage` class is used to display an image in a text-based format, such as ASCII art.
 * It leverages the `ImageRenderingVisitor` to traverse the image data and generate its string representation.
 *
 * @param image the `Image[Char]` to be rendered as text
 */
class ImagePage (image :Image[Char]) extends TextPage{

  /**
   * Renders the image using the `ImageRenderingVisitor`.
   *
   * @return a string representation of the image
   */
  override def render(): String = {
    val visitor = new ImageRenderingVisitor[Char]
    image.accept(visitor)
  }
}
