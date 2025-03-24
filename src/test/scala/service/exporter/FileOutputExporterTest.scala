package service.exporter

import org.scalatest.funsuite.AnyFunSuite
import java.io.{File, FileInputStream, IOException}
import java.nio.file.{Files, Paths}
import service.exporter.text.FileOutputExporter

class FileOutputExporterTest extends AnyFunSuite {

  test("FileOutputExporter should write text to a file") {
    // Create a temporary file
    val tempFile = File.createTempFile("testFileOutputExporter", ".txt")
    tempFile.deleteOnExit() // Ensure it is deleted after the test

    val exporter = new FileOutputExporter(tempFile)

    // Test exporting text
    val text = "Hello, FileOutputExporter!"
    exporter.exportFile(text)

    // Read content from the file and verify it matches the exported text
    val fileContent = new String(Files.readAllBytes(tempFile.toPath), "UTF-8")
    assert(fileContent === text)

    exporter.close()
  }

  test("FileOutputExporter should write text to a new file") {
    // Specify the path for the test file
    val testFilePath = "testFileOutputExporter.txt"
    val testFile = new File(testFilePath)

    // Ensure the file does not exist before the test
    if (testFile.exists()) testFile.delete()

    // Initialize FileOutputExporter with the specified file
    val exporter = new FileOutputExporter(testFile)

    // Text to be written to the file
    val text = "Hello, FileOutputExporter!"

    // Write text to file
    exporter.exportFile(text)

    // Close the exporter to flush and save the content
    exporter.close()

    // Read content from the file and verify it matches the exported text
    val fileContent = new String(Files.readAllBytes(testFile.toPath), "UTF-8")
    assert(fileContent === text)

    // Clean up: delete the test file after verification
    testFile.delete()
  }

  test("FileOutputExporter should throw an exception if closed and then written to") {
    val tempFile = File.createTempFile("testFileOutputExporter", ".txt")
    tempFile.deleteOnExit()

    val exporter = new FileOutputExporter(tempFile)

    // Close the exporter
    exporter.close()

    // Attempt to export text after closing, expecting an exception
    val thrown = intercept[Exception] {
      exporter.exportFile("This should fail")
    }
    assert(thrown.getMessage === "The stream is already closed")
  }

  test("FileOutputExporter should be able to handle large text export") {
    val tempFile = File.createTempFile("testFileOutputExporter", ".txt")
    tempFile.deleteOnExit()

    val exporter = new FileOutputExporter(tempFile)

    // Create a large text string
    val largeText = "A" * 1000000 // 1 million 'A' characters
    exporter.exportFile(largeText)

    // Read content from the file and verify it matches the large text
    val fileContent = new String(Files.readAllBytes(tempFile.toPath), "UTF-8")
    assert(fileContent === largeText)

    exporter.close()
  }

  test("FileOutputExporter close should be idempotent (safe to call multiple times)") {
    val tempFile = File.createTempFile("testFileOutputExporter", ".txt")
    tempFile.deleteOnExit()

    val exporter = new FileOutputExporter(tempFile)

    // Close the exporter twice; it should not throw an exception
    exporter.close()
    exporter.close() // Calling close again should not cause an error

    // No assertion needed if no exception is thrown; just ensure no errors
  }
  
}
