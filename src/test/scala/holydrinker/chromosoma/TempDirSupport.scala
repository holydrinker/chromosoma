package holydrinker.chromosoma

import java.io.File
import java.nio.file.Files

import org.apache.commons.io.FileUtils

trait TempDirSupport {

  def withTempDir(body: File => Unit) = {
    val tempDir = Files.createTempDirectory("chromosoma-unit-test").toFile
    body(tempDir)
    FileUtils.deleteDirectory(tempDir)
  }

}
