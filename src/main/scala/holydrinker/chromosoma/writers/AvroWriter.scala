package holydrinker.chromosoma.writers

import java.io.File

import holydrinker.chromosoma.model.Dataset
import org.apache.avro.file.DataFileWriter
import org.apache.avro.generic.{ GenericDatumWriter, GenericRecord }

/**
  * Exposes utilities to export the generated datasets in avro format.
  */
class AvroWriter extends DatasetWriter {

  /**
    * Writes the dataset to disk in avro format.
    * @param dataset the dataset
    * @param path the path
    */
  override def saveFile(dataset: Dataset, path: String): Unit = {
    val extensionPath  = s"$path.$extension"
    val file           = new File(extensionPath)
    val datumWriter    = new GenericDatumWriter[GenericRecord](dataset.schema)
    val dataFileWriter = new DataFileWriter[GenericRecord](datumWriter)
    dataFileWriter.create(dataset.schema, file)
    dataset.rows.foreach(dataFileWriter.append)
    dataFileWriter.close()
  }

  /**
    * Specify the extension of the avro file to be written.
    * @return the extension
    */
  override def extension = "avro"

}
