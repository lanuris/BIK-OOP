package console.view.page.generic

import console.view.page.TextPage

/**
 * A concrete implementation of `TextPage` for rendering error messages.
 *
 * The `ErrorResponse` class is used to represent error messages in a textual format.
 * It renders a message prefixed with "Error:" and appends a newline character.
 *
 * @param message the error message to display (default is "Error")
 */
class ErrorResponse(message: String = "Error") extends TextPage
{
  /**
   * Renders the error message as a string.
   *
   * @return a string prefixed with "Error:" followed by the error message and a newline
   */
  override def render(): String = "Error: " + message + "\n"
}
