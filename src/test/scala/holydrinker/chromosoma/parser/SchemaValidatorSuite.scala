package holydrinker.chromosoma.parser

import holydrinker.chromosoma.model.{
  ChromoBoolean,
  ChromoDecimal,
  ChromoField,
  ChromoInt,
  ChromoSchema,
  ChromoString,
  Rule
}
import munit.FunSuite

class SchemaValidatorSuite extends FunSuite {

  test("Valid datatype validation") {
    val parsedSchema = ParsedChromoSchema(
      List(
        ParsedChromoField("name", "string", List.empty[Rule]),
        ParsedChromoField("age", "int", List.empty[Rule]),
        ParsedChromoField("budget", "decimal", List.empty[Rule]),
        ParsedChromoField("married", "boolean", List.empty[Rule])
      )
    )

    val actual = SchemaValidator.validateFieldTypes(parsedSchema)
    val expected = Right(
      ChromoSchema(
        List(
          ChromoField("name", ChromoString, List.empty[Rule]),
          ChromoField("age", ChromoInt, List.empty[Rule]),
          ChromoField("budget", ChromoDecimal, List.empty[Rule]),
          ChromoField("married", ChromoBoolean, List.empty[Rule])
        )
      )
    )

    assert(actual == expected)
  }

  test("Invalid datatype validation") {
    val parsedSchema = ParsedChromoSchema(
      List(
        ParsedChromoField("name", "string", List.empty[Rule]),
        ParsedChromoField("age", "int", List.empty[Rule]),
        ParsedChromoField("budget", "decimal", List.empty[Rule]),
        ParsedChromoField("amazing_field", "amazing_type", List.empty[Rule])
      )
    )

    val actual   = SchemaValidator.validateFieldTypes(parsedSchema)
    val expected = Left("Unknown type in field amazing_field: amazing_type")

    assert(actual == expected)
  }

}
