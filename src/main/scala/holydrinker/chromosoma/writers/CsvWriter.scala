package holydrinker.chromosoma.writers

import java.io.{ BufferedWriter, File, FileWriter }
import holydrinker.chromosoma.model.Dataset
import org.apache.avro.generic.GenericRecord
import scala.collection.JavaConverters._

class CsvWriter(sep: String = ",") extends DatasetWriter {

  override def save(dataset: Dataset, path: String): Unit = {
    val headers = dataset.schema.getFields.asScala.map(_.name())
    val lines   = dataset.rows.map(recordToFlatString(_, headers))

    val bw = new BufferedWriter(new FileWriter(new File(path)))
    lines.foreach(line => bw.write(line + "\n"))
    bw.close()
  }

  private def recordToFlatString(record: GenericRecord, keys: Seq[String]): String =
    keys
      .map(record.get(_).toString())
      .mkString(sep)

}
