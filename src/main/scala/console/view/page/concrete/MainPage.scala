package console.view.page.concrete

import console.view.page.TextPage

/**
 * A concrete implementation of `TextPage` for rendering the main page.
 *
 * The `MainPage` class represents the initial page displayed to the user when the application starts.
 * It provides a welcome message and basic guidance for using the application.
 */
class MainPage extends TextPage{

  /**
   * Renders the main text page with a welcome message.
   *
   * @return a string containing the main page content, including a title and instructions for help
   */
  override def render(): String = {
    s"""
       |----- ASCII ART APPLICATION -----
       |
       |Welcome to the ASCII Art Application!
       |
       |This tool allows you to convert images into ASCII art, apply filters, and export your results.
       |If you're unsure where to start, run the application with the '--help' argument to view detailed instructions.
       |
       |""".stripMargin
  }
}
