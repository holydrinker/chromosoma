package holydrinker.chromosoma.model

import cats.data.Validated
import holydrinker.chromosoma.error.ChromoError
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
  def fromFields(fields: List[ParsedChromoField]): Validated[ChromoError, ChromoSchema] = {
    val schema = ParsedChromoSchema(fields)
    ValidationService.validate(schema)
  }

}
