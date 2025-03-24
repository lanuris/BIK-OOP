package service.exporter.text

import service.exporter.Exporter

/**
 * A specialized trait for exporting text data to a file.
 *
 * This trait extends the generic `Exporter` trait, specifying the type of data to export as `String`.
 * Concrete implementations of this trait should define the logic for exporting text to a file, 
 * such as writing content to a text file in a specified format.
 */
trait TextExporter extends Exporter[String]{
  
}
