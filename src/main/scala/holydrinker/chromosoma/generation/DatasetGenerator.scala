package holydrinker.chromosoma.generation

import cats.data.Validated
import cats.data.Validated.Valid
import custom.{GenericCaseClass, RecordGenerator}
import holydrinker.chromosoma.error.ChromoError
import holydrinker.chromosoma.logging.ChromoLogger
import holydrinker.chromosoma.model.{ChromoSchema, Dataset}

/**
  * Exposes utilities to work with [[Dataset]]
  */
class DatasetGenerator[T <: GenericCaseClass](recordGenerator: RecordGenerator[T]) extends ChromoLogger {

  /**
    * Maybe generates a dataset knowing the source schema and the number of instances.
    *
    * @param chromoSchema source [ChromoSchema]
    * @param instances number of instances
    * @return the [[Dataset]]
    */
  def fromSchema(chromoSchema: ChromoSchema, instances: Long): Validated[ChromoError, Dataset] = {
    logInfo("Apply generation rules")
    val rows       = (0 until instances.toInt).map(_ => recordGenerator.makeGenericRecord(chromoSchema))
    Valid(Dataset(rows))
  }


}
