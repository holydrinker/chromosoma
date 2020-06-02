package holydrinker.chromosoma.writers

import java.io.File

import holydrinker.chromosoma.model.Dataset
import org.apache.avro.file.DataFileWriter
import org.apache.avro.generic.{ GenericDatumWriter, GenericRecord }

class AvroWriter extends DatasetWriter {

  override def save(dataset: Dataset, path: String): Unit = {
    val file           = new File(path)
    val datumWriter    = new GenericDatumWriter[GenericRecord](dataset.schema)
    val dataFileWriter = new DataFileWriter[GenericRecord](datumWriter)
    dataFileWriter.create(dataset.schema, file)
    dataset.rows.foreach(dataFileWriter.append)
    dataFileWriter.close()
  }

}
