package service.exporter

import org.scalatest.funsuite.AnyFunSuite
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import service.exporter.text.StreamTextExporter

class StreamTextExporterTest extends AnyFunSuite {

  test("StreamTextExporter should export text to output stream") {
    // Set up ByteArrayOutputStream to capture output
    val outputStream = new ByteArrayOutputStream()
    val exporter = new StreamTextExporter(outputStream)

    // Test exporting text
    val text = "Hello, world!"
    exporter.exportFile(text)

    // Verify the exported text matches the input
    assert(outputStream.toString("UTF-8") === text)
  }

  test("StreamTextExporter should throw an exception if closed and then written to") {
    val outputStream = new ByteArrayOutputStream()
    val exporter = new StreamTextExporter(outputStream)

    // Close the exporter
    exporter.close()

    // Attempt to export text after closing, expecting an exception
    val thrown = intercept[Exception] {
      exporter.exportFile("This should fail")
    }
    assert(thrown.getMessage === "The stream is already closed")
  }

  test("StreamTextExporter close should not throw exception if called multiple times") {
    val outputStream = new ByteArrayOutputStream()
    val exporter = new StreamTextExporter(outputStream)

    // Closing multiple times should not throw an exception
    exporter.close()
    exporter.close() // should be idempotent, no exception expected

    // No assertion needed here if no exception is thrown
  }
}
