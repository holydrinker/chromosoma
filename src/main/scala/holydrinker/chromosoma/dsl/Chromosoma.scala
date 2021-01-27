package holydrinker.chromosoma.dsl

import holydrinker.chromosoma.error.{ ChromoError, ChromoMessages }
import holydrinker.chromosoma.logging.ChromoLogger
import holydrinker.chromosoma.model.{
  ChromoField,
  ChromoSchema,
  ChromoString,
  ChromoType,
  Dataset,
  Rule,
  StringSetRule
}
import holydrinker.chromosoma.parser.SemanticsService
import holydrinker.chromosoma.writers.Format

/**
  * Exposes APIs to start building datasets.
  */
object Chromosoma {

  /**
    * Specify the number of target instance of the dataset to build
    * @param n the number of instances
    * @return the builder
    */
  def instances(n: Long): ChromosomaBuilder =
    new ChromosomaBuilder(n, List.empty[ChromoField])
}

/**
  * Builder object to incrementally build datasets
  * @param instances number of rows to generate
  * @param fields information about columns
  */
class ChromosomaBuilder(
    instances: Long,
    fields: List[ChromoField]
) extends ChromoLogger {

  /**
    * Adds a new field to the schema
    *
    * @param name the field name
    * @param dataType the field type
    * @param rules the field rules
    * @return
    */
  def withField(
      name: String,
      dataType: ChromoType,
      rules: List[Rule]
  ): ChromosomaBuilder = {
    val newField = ChromoField(name, dataType, rules)
    new ChromosomaBuilder(
      instances = this.instances,
      fields = this.fields :+ newField
    )
  }

  /**
    * Safely generate the dataset
    * @return maybe a [[Dataset]]
    */
  def generate(): Either[ChromoError, Dataset] =
    if (this.fields.isEmpty) {
      Left(ChromoError(ChromoMessages.cannotGenerate))
    } else {
      val schema = ChromoSchema(this.fields)
      SemanticsService
        .validateSchema(schema)
        .flatMap(validSchema => Dataset.fromSchema(validSchema, this.instances).toEither)
    }

  /**
    * Unsafely generate the dataset
    * @return a [[Dataset]]
    */
  def generateUnsafe(): Dataset =
    if (this.fields.isEmpty) {
      throw ChromoError(ChromoMessages.cannotGenerate)
    } else {
      val maybeDataset =
        SemanticsService
          .validateSchema(ChromoSchema(this.fields))
          .flatMap(validSchema => Dataset.fromSchema(validSchema, this.instances).toEither)

      maybeDataset match {
        case Right(dataset) =>
          dataset
        case Left(error) =>
          logError(error.msg)
          throw error
      }
    }

}
