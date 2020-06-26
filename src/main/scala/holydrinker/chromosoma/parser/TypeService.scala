package holydrinker.chromosoma.parser

import holydrinker.chromosoma.model.{ ChromoBoolean, ChromoDecimal, ChromoField, ChromoInt, ChromoSchema, ChromoString }
import cats.implicits._

object TypeService {

  def validateParsedSchema(schema: ParsedChromoSchema): Either[String, ChromoSchema] =
    schema.fields
      .traverse(validateSingleFieldType)
      .map(ChromoSchema(_))

  private def validateSingleFieldType(field: ParsedChromoField): Either[String, ChromoField] =
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
        Left(s"Unknown type in field $name: $invalidType")
    }
}
