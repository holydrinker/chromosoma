package holydrinker.chromosoma.model

import holydrinker.chromosoma.generation.Generation
import holydrinker.chromosoma.schema.{ Boolean, ChromoSchema, Decimal, Field, Int, String }
import holydrinker.chromosoma.writers.DatasetWriter
import org.apache.avro.Schema
import org.apache.avro.generic.{ GenericData, GenericRecord }

case class Dataset(rows: Seq[GenericRecord], schema: Schema)

object Dataset {

  def fromSchema(chromoSchema: ChromoSchema, n: Int): Dataset = {
    val avroSchema = ChromoSchema.toAvroSchema(chromoSchema)
    val rows       = (0 to n).map(_ => makeGenericRecord(chromoSchema, avroSchema))
    Dataset(rows, avroSchema)
  }

  private def makeGenericRecord(chromoSchema: ChromoSchema, avroSchema: Schema): GenericRecord = {
    val record = new GenericData.Record(avroSchema)
    chromoSchema.fields.foreach {
      case Field(name, String) =>
        record.put(name, Generation.generateString(10))
      case Field(name, Int) =>
        record.put(name, Generation.generateInteger)
      case Field(name, Decimal) =>
        record.put(name, Generation.generateNumeric)
      case Field(name, Boolean) =>
        record.put(name, Generation.generateBoolean)
    }
    record
  }

  def save(dataset: Dataset, pathname: String, format: String): Unit =
    DatasetWriter(format).save(dataset, pathname)

}
