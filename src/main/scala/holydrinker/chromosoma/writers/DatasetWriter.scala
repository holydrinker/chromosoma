package holydrinker.chromosoma.writers

import holydrinker.chromosoma.model.Dataset

trait DatasetWriter {

  def save(dataset: Dataset, path: String)

}

object DatasetWriter {

  def apply(format: String): DatasetWriter = format match {
    case "avro" => new AvroWriter
    case "csv"  => new CsvWriter
  }

}
