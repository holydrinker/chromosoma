package holydrinker.chromosoma.parser

import cats.Traverse
import holydrinker.chromosoma.model.{ ChromoBoolean, ChromoDecimal, ChromoField, ChromoInt, ChromoSchema, ChromoString }
import cats.implicits._
import cats.instances.list._
import cats.instances.either._

object SchemaValidator {

  type InvalidTypeInSchemaError = String

  def validate(schema: ParsedChromoSchema): Either[Error, ChromoSchema] =
    null

  private def validateDataType(schema: ParsedChromoSchema): Either[InvalidTypeInSchemaError, ChromoSchema] = {
    val validFields: List[Either[InvalidTypeInSchemaError, ChromoField]] = schema.fields.map {
      case ParsedChromoField(name, "int", rules) =>
        Right(ChromoField(name, ChromoInt, rules))
      case ParsedChromoField(name, "string", rules) =>
        Right(ChromoField(name, ChromoString, rules))
      case ParsedChromoField(name, "decimal", rules) =>
        Right(ChromoField(name, ChromoDecimal, rules))
      case ParsedChromoField(name, "boolean", rules) =>
        Right(ChromoField(name, ChromoBoolean, rules))
      case ParsedChromoField(name, _, rules) =>
        Left("invalid type")
    }

    Traverse[List].flatTraverse(validFields) {
      case Right(value) => Right(ChromoSchema(List(value)))
      case Left(_)      => Left("invalid schema")
    }

    val y = Traverse[List].traverse(validFields)

    null // con una applicative o traverse devo trasformare la lista di right di field in un right di field
  }

}
