package holydrinker.chromosoma.parser

import holydrinker.chromosoma.error.ValidationError
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

class TypeServiceSuite extends FunSuite {

  test("Valid schema validation") {
    val schema =
      ParsedChromoSchema(
        List(
          ParsedChromoField("name", "string", List.empty[Rule]),
          ParsedChromoField("age", "int", List.empty[Rule]),
          ParsedChromoField("budget", "decimal", List.empty[Rule]),
          ParsedChromoField("married", "boolean", List.empty[Rule])
        )
      )

    val expected =
      ChromoSchema(
        List(
          ChromoField("name", ChromoString, List.empty[Rule]),
          ChromoField("age", ChromoInt, List.empty[Rule]),
          ChromoField("budget", ChromoDecimal, List.empty[Rule]),
          ChromoField("married", ChromoBoolean, List.empty[Rule])
        )
      )

    val actual = TypeService.validateParsedSchema(schema)

    assert(Right(expected) == actual)
  }

  test("Invalid schema validation") {
    val schema =
      ParsedChromoSchema(
        List(
          ParsedChromoField("name", "string", List.empty[Rule]),
          ParsedChromoField("age", "int", List.empty[Rule]),
          ParsedChromoField("budget", "decimal", List.empty[Rule]),
          ParsedChromoField("invalid_field", "invalid_type", List.empty[Rule])
        )
      )

    val expected = Left(ValidationError("Unknown type in field invalid_field: invalid_type"))

    val actual = TypeService.validateParsedSchema(schema)

    assert(expected == actual)
  }

}
