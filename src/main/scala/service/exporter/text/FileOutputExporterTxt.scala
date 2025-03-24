package service.exporter.text

import java.io.File

/**
 * A specialized implementation of `FileOutputExporter` that ensures the file being exported
 * always has a `.txt` extension.
 *
 * This class validates the file name and ensures that it ends with `.txt`. If the provided file
 * does not have the correct extension, an `IllegalArgumentException` is thrown to maintain
 * consistency in file format handling.
 *
 * Core functionality for writing text data to the file is inherited from `FileOutputExporter`,
 * including UTF-8 encoding and efficient resource management.
 *
 * @param filePath the target file path for exporting text data
 * @throws IllegalArgumentException if the provided file path does not have a `.txt` extension
 */
class FileOutputExporterTxt(filePath: String) extends FileOutputExporter({
  val file = new File(filePath)
  if (!file.getName.toLowerCase.endsWith(".txt")) {
    throw new IllegalArgumentException("Export file must have a .txt extension")
  }
  file
}) {
  
}

