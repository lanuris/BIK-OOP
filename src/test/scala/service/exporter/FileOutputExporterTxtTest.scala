package service.exporter

import org.scalatest.funsuite.AnyFunSuite
import java.io.{File, FileInputStream, IOException}
import java.nio.file.{Files, Paths}
import service.exporter.text.FileOutputExporterTxt

class FileOutputExporterTxtTest extends AnyFunSuite  {

  test("FileOutputExporterTxt should throw exception when file extension not .txt") {
    val tempFile = File.createTempFile("testFileOutputExporter", ".file")
    tempFile.deleteOnExit()

    // Attempt to create instance with wrong extension, expecting an exception
    val thrown = intercept[Exception] {
      val exporter = new FileOutputExporterTxt(tempFile.getPath)
    }
  }

}
