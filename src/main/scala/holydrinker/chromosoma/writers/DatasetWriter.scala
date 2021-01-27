package holydrinker.chromosoma.writers

import holydrinker.chromosoma.logging.ChromoLogger
import holydrinker.chromosoma.model.Dataset

/**
  * Exposes utilities to export the generated datasets.
  */
trait DatasetWriter extends ChromoLogger {

  /**
    * Writes the dataset to disk.
    * @param dataset the dataset
    * @param path the path
    */
  def save(dataset: Dataset, path: String): Unit = {
    val extensionPath = makeExtensionPath(path)
    saveFile(dataset, extensionPath)
    logInfo(s"File saved: $path.$extension")
  }

  /**
    * Implements the internal logic to write the dataset to disk.
    * @param dataset the dataset
    * @param path the path
    */
  protected def saveFile(dataset: Dataset, path: String)

  /**
    * Specify the extension of the file to be written.
    * @return the extension
    */
  protected def extension: String

  /**
    * Ensures that the path ends with the extension.
    * For example myfile.csv
    * @param path
    * @return
    */
  protected def makeExtensionPath(path: String): String =
    if (!path.endsWith(extension))
      s"$path.$extension"
    else
      path

}

object DatasetWriter {

  def apply(format: String): DatasetWriter = format match {
    case Format.Avro => new AvroWriter
    case Format.Csv  => new CsvWriter
  }

}
