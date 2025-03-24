package console.view.page

/**
 * A generic trait for defining a page with renderable content.
 *
 * The `Page` trait represents a conceptual page that can render its content into
 * a specific data type, `T`. This allows flexibility in how pages are presented,
 * such as rendering text, HTML, or other formats, depending on the implementation.
 *
 * @tparam T the data type of the rendered content (e.g., `String`, `HTML`, etc.)
 */
trait Page[T]
{
  /**
   * Renders the content of the page.
   *
   * @return the rendered content of the page as an instance of type `T`
   */
  def render(): T
}
