package holydrinker.chromosoma.model

import holydrinker.chromosoma.generation.GenerationService
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
    val record           = new GenericData.Record(avroSchema)
    val generatorService = new GenerationService(chromoSchema.rules)

    chromoSchema.fields.foreach {
      case ChromoField(name, ChromoString) =>
        record.put(name, generatorService.generateString(10))
      case ChromoField(name, ChromoInt) =>
        record.put(name, generatorService.generateInteger)
      case ChromoField(name, ChromoDecimal) =>
        record.put(name, generatorService.generateNumeric)
      case ChromoField(name, ChromoBoolean) =>
        record.put(name, generatorService.generateBoolean)
    }
    record
  }

  def save(dataset: Dataset, pathname: String, format: String): Unit =
    DatasetWriter(format).save(dataset, pathname)

}
