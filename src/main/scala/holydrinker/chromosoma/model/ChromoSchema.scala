package holydrinker.chromosoma.model

import cats.data.Validated
import holydrinker.chromosoma.error.ValidationError
import holydrinker.chromosoma.parser.{ ParsedChromoField, ParsedChromoSchema, ValidationService }
import org.apache.avro.Schema

/**
  * Represents a feature of the target dataset's schema.
  *
  * @param name feature's name
  * @param dataType feature's type
  * @param rules feature's generation rules
  */
case class ChromoField(name: String, dataType: ChromoType, rules: List[Rule] = List.empty[Rule])

/**
  * Represents the schema of the target dataset.
  *
  * @param fields list of [[ChromoField]]
  */
case class ChromoSchema(fields: List[ChromoField])

/**
  * Exposes utilities to work with [[ChromoSchema]].
  */
object ChromoSchema {

  /**
    * Can create a [[ChromoSchema]] from a list of [[ParsedChromoField]].
    * A schema is built only if the semantics of each field makes sense.
    *
    * @param fields fields written with correct syntax
    * @return fields with correct semantics
    */
  def fromFields(fields: List[ParsedChromoField]): Validated[ValidationError, ChromoSchema] = {
    val schema = ParsedChromoSchema(fields)
    ValidationService.validate(schema)
  }

  /**
    * Converts a [[ChromoSchema]] to an Avro [[Schema]]
    * @param chromoSchema chromo schema
    * @return avro schema
    */
  def toAvroSchema(chromoSchema: ChromoSchema): Schema = {
    val fieldTemplate = "{\"name\": \"%s\", \"type\": \"%s\"}"
    val fields = chromoSchema.fields
      .map {
        case ChromoField(name, ChromoString, _) =>
          fieldTemplate.format(name, "string")
        case ChromoField(name, ChromoDecimal, _) =>
          fieldTemplate.format(name, "double")
        case ChromoField(name, ChromoInt, _) =>
          fieldTemplate.format(name, "int")
        case ChromoField(name, ChromoBoolean, _) =>
          fieldTemplate.format(name, "boolean")
      }
      .mkString(", ")

    val rowSchema =
      s"""{
        |"type": "record", 
        |"name": "chromorecord", 
        |"fields": [$fields]
        |}""".stripMargin

    new Schema.Parser().parse(rowSchema)
  }

}
