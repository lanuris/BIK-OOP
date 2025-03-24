package console.view.page

/**
 * A specialized trait for pages that render content as text.
 *
 * The `TextPage` trait extends the `Page` trait, specifically for pages where the rendered content
 * is represented as a `String`. This is commonly used for console-based applications or scenarios
 * where textual representation of the page is required.
 */
trait TextPage extends Page[String]
{
  
}
