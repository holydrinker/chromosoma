package holydrinker.chromosoma.parser

import cats.data.Validated
import cats.implicits._
import holydrinker.chromosoma.model.{ ChromoBoolean, ChromoDecimal, ChromoField, ChromoInt, ChromoSchema, ChromoString }
import holydrinker.chromosoma.error.ValidationError

/**
  * Exposes utilities to validate a well written schema.
  */
object ValidationService {

  private final val IntType     = "int"
  private final val StringType  = "string"
  private final val DecimalType = "decimal"
  private final val BooleanType = "boolean"

  /**
    * Builds a [[ChromoSchema]] if the semantics of a well written schema makes sense.
    *
    * @param schema schema with correct syntax
    * @return maybe the schema with correct semantics
    */
  def validate(schema: ParsedChromoSchema): Validated[ValidationError, ChromoSchema] =
    schema.fields
      .traverse(validateSingleFieldType)
      .map(ChromoSchema(_))
      .flatMap(schema => SemanticsService.validateSchema(schema))
      .toValidated

  private def validateSingleFieldType(field: ParsedChromoField): Either[ValidationError, ChromoField] =
    field match {
      case ParsedChromoField(name, IntType, rules) =>
        Right(ChromoField(name, ChromoInt, rules))
      case ParsedChromoField(name, StringType, rules) =>
        Right(ChromoField(name, ChromoString, rules))
      case ParsedChromoField(name, DecimalType, rules) =>
        Right(ChromoField(name, ChromoDecimal, rules))
      case ParsedChromoField(name, BooleanType, rules) =>
        Right(ChromoField(name, ChromoBoolean, rules))
      case ParsedChromoField(name, invalidType, _) =>
        Left(ValidationError(s"Unknown type in field $name: $invalidType"))
    }

}
