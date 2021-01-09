package holydrinker.chromosoma.writers

import java.io.{ BufferedWriter, File, FileWriter }
import holydrinker.chromosoma.model.Dataset
import org.apache.avro.generic.GenericRecord
import scala.collection.JavaConverters._

/**
  * Exposes utilities to export the generated datasets in csv format.
  */
class CsvWriter(sep: String = ",") extends DatasetWriter {

  /**
    * Writes the dataset to disk in csv format.
    * @param dataset the dataset
    * @param path the path
    */
  override def save(dataset: Dataset, path: String): Unit = {
    val extensionPath = s"$path.$extension"
    val headers       = dataset.schema.getFields.asScala.map(_.name())
    val lines         = dataset.rows.map(recordToFlatString(_, headers))

    val bw = new BufferedWriter(new FileWriter(new File(extensionPath)))
    lines.foreach(line => bw.write(line + "\n"))
    bw.close()
  }

  /**
    * Specify the extension of the csv file to be written.
    * @return the extension
    */
  override def extension: String = "csv"

  private def recordToFlatString(record: GenericRecord, keys: Seq[String]): String =
    keys
      .map(record.get(_).toString())
      .mkString(sep)

}
