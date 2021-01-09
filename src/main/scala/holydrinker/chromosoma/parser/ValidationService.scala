package holydrinker.chromosoma.parser

import holydrinker.chromosoma.model.{ ChromoBoolean, ChromoDecimal, ChromoField, ChromoInt, ChromoSchema, ChromoString }
import cats.implicits._
import holydrinker.chromosoma.error.ValidationError

/**
  * Exposes utilities to validate a well written schema.
  */
object ValidationService {

  /**
    * Builds a [[ChromoSchema]] if the semantics of a well written schema makes sense.
    *
    * @param schema schema with correct syntax
    * @return maybe the schema with correct semantics
    */
  def validate(schema: ParsedChromoSchema): Either[Exception, ChromoSchema] =
    schema.fields
      .traverse(validateSingleFieldType)
      .map(ChromoSchema(_))
      .flatMap(schema => SemanticsService.validateSchema(schema))

  private def validateSingleFieldType(field: ParsedChromoField): Either[ValidationError, ChromoField] =
    field match {
      case ParsedChromoField(name, "int", rules) =>
        Right(ChromoField(name, ChromoInt, rules))
      case ParsedChromoField(name, "string", rules) =>
        Right(ChromoField(name, ChromoString, rules))
      case ParsedChromoField(name, "decimal", rules) =>
        Right(ChromoField(name, ChromoDecimal, rules))
      case ParsedChromoField(name, "boolean", rules) =>
        Right(ChromoField(name, ChromoBoolean, rules))
      case ParsedChromoField(name, invalidType, _) =>
        Left(ValidationError(s"Unknown type in field $name: $invalidType"))
    }
}
