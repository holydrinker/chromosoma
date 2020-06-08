package holydrinker.chromosoma.model

import holydrinker.chromosoma.generation.GenerationService
import holydrinker.chromosoma.writers.DatasetWriter
import org.apache.avro.Schema
import org.apache.avro.generic.{ GenericData, GenericRecord }

case class Dataset(rows: Seq[GenericRecord], schema: Schema)

object Dataset {

  def fromSchema(chromoSchema: ChromoSchema, instances: Int): Dataset = {
    val avroSchema = ChromoSchema.toAvroSchema(chromoSchema)
    val rows       = (0 to instances).map(_ => makeGenericRecord(chromoSchema, avroSchema))
    Dataset(rows, avroSchema)
  }

  private def makeGenericRecord(chromoSchema: ChromoSchema, avroSchema: Schema): GenericRecord = {
    val record = new GenericData.Record(avroSchema)

    chromoSchema.fields.foreach {
      case ChromoField(name, ChromoString, _) =>
        record.put(name, GenerationService.generateString(10))
      case ChromoField(name, ChromoInt, rules) =>
        record.put(name, GenerationService.generateInteger(rules))
      case ChromoField(name, ChromoDecimal, _) =>
        record.put(name, GenerationService.generateNumeric)
      case ChromoField(name, ChromoBoolean, _) =>
        record.put(name, GenerationService.generateBoolean)
    }
    record
  }

  def save(dataset: Dataset, pathname: String, format: String): Unit =
    DatasetWriter(format).save(dataset, pathname)

}
