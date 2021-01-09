package holydrinker.chromosoma.model

import holydrinker.chromosoma.generation.GenerationService
import holydrinker.chromosoma.writers.DatasetWriter
import org.apache.avro.Schema
import org.apache.avro.generic.{ GenericData, GenericRecord }

/**
  * Represents a dataset with its row and columns information.
  *
  * @param rows rows information
  * @param schema columns information
  */
case class Dataset(rows: Seq[GenericRecord], schema: Schema)

/**
  * Exposes utilities to work with [[Dataset]]
  */
object Dataset {

  /**
    * Maybe generates a dataset knowing the source schema and the number of instances.
    *
    * @param chromoSchema source [[ChromoSchema]
    * @param instances number of instances
    * @return the [[Dataset]]
    */
  def fromSchema(chromoSchema: ChromoSchema, instances: Int): Either[String, Dataset] = {
    val avroSchema = ChromoSchema.toAvroSchema(chromoSchema)
    val rows       = (0 to instances).map(_ => makeGenericRecord(chromoSchema, avroSchema))
    Right(Dataset(rows, avroSchema))
  }

  private def makeGenericRecord(chromoSchema: ChromoSchema, avroSchema: Schema): GenericRecord = {
    val record = new GenericData.Record(avroSchema)

    chromoSchema.fields.foreach {
      case ChromoField(name, ChromoString, rules) =>
        record.put(name, GenerationService.generateString(rules))

      case ChromoField(name, ChromoInt, rules) =>
        record.put(name, GenerationService.generateInteger(rules))

      case ChromoField(name, ChromoDecimal, rules) =>
        record.put(name, GenerationService.generateDecimal(rules))

      case ChromoField(name, ChromoBoolean, rules) =>
        record.put(name, GenerationService.generateBoolean(rules))
    }
    record
  }

  /**
    * Saves the generated dataset.
    * @param dataset the generated dataset
    * @param pathname the path to save the dataset
    * @param format the format to save the dataset
    */
  def save(dataset: Dataset, pathname: String, format: String): Unit =
    DatasetWriter(format).save(dataset, pathname)

}
