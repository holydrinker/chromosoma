package holydrinker.chromosoma.writers

import holydrinker.chromosoma.model.Dataset

/**
  * Exposes utilities to export the generated datasets.
  */
trait DatasetWriter {

  /**
    * Writes the dataset to disk.
    * @param dataset the dataset
    * @param path the path
    */
  def save(dataset: Dataset, path: String)

  /**
    * Specify the extension of the file to be written.
    * @return the extension
    */
  def extension: String

}

object DatasetWriter {

  def apply(format: String): DatasetWriter = format match {
    case "avro" => new AvroWriter
    case "csv"  => new CsvWriter
  }

}
