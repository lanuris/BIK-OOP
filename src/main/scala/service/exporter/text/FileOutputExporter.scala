package service.exporter.text

import java.io.{File, FileOutputStream}

/**
 * A concrete implementation of `StreamTextExporter` for exporting text to a file.
 *
 * This class writes text data to a specified file. 
 * Uses `FileOutputStream` for writing the text content. It inherits all functionality from 
 * `StreamTextExporter`, including UTF-8 encoding and resource management.
 *
 * @param file the file to which the text will be exported
 */
class FileOutputExporter(file: File) extends StreamTextExporter (new FileOutputStream(file)) {
  
}

