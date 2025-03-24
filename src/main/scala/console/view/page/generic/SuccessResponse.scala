package console.view.page.generic

import console.view.page.TextPage

/**
 * A concrete implementation of `TextPage` for rendering success messages.
 *
 * The `SuccessResponse` class is used to represent success messages in a textual format.
 * It renders a message prefixed with "Success:" and appends a newline character.
 *
 * @param message the success message to display (default is "Ok")
 */
class SuccessResponse(message: String = "Ok") extends TextPage
{
  /**
   * Renders the success message as a string.
   *
   * @return a string prefixed with "Success:" followed by the success message and a newline
   */
  override def render(): String = "Success: " + message + "\n"
}
