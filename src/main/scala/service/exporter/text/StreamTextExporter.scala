package service.exporter.text

import java.io.OutputStream

/**
 * A concrete implementation of `TextExporter` for exporting text data to an `OutputStream`.
 *
 * This class provides functionality to write text content to an `OutputStream` and ensures proper
 * resource management by allowing the stream to be closed when finished. It supports exporting
 * text data encoded in UTF-8.
 *
 * @param outputStream the `OutputStream` to which the text data will be written
 */
class StreamTextExporter(outputStream: OutputStream) extends TextExporter {

  /**
   * A flag to track whether the stream has been closed.
   */
  private var closed = false

  /**
   * Writes the provided text to the `OutputStream`.
   *
   * @param text the text to write to the stream
   * @throws Exception if the stream is already closed or if an error occurs during writing
   */
  protected def exportToStream(text: String): Unit = {

    if (closed)
      throw new Exception("The stream is already closed")

    outputStream.write(text.getBytes("UTF-8"))
    outputStream.flush()
  }

  /**
   * Closes the `OutputStream` to release any associated resources.
   *
   * Once closed, the `StreamTextExporter` cannot be used to write additional data.
   *
   * @throws Exception if an error occurs during the stream closure
   */
  def close(): Unit = {
    if (closed)
      return

    outputStream.close()
    closed = true
  }

  /**
   * Exports the provided text to the `OutputStream`.
   *
   * @param item the text content to be exported
   * @throws Exception if the stream is closed or an error occurs during export
   */
  override def exportFile (item: String): Unit
  = exportToStream(item)
}
  

