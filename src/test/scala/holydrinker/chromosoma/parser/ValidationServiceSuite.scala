package holydrinker.chromosoma.parser

import cats.data.Validated.{ Invalid, Valid }
import holydrinker.chromosoma.error.ValidationError
import holydrinker.chromosoma.model.{
  BooleanRule,
  ChromoBoolean,
  ChromoDecimal,
  ChromoField,
  ChromoInt,
  ChromoSchema,
  ChromoString,
  RangeRule,
  Rule,
  StringSetRule
}
import munit.FunSuite

class ValidationServiceSuite extends FunSuite {

  test("Valid datatype validation") {
    val parsedSchema = ParsedChromoSchema(
      List(
        ParsedChromoField("name", "string", List(StringSetRule(Set("dave"), 1.0))),
        ParsedChromoField("age", "int", List(RangeRule(20, 30, 1.0))),
        ParsedChromoField("budget", "decimal", List(RangeRule(2000, 3000, 1.0))),
        ParsedChromoField("married", "boolean", List(BooleanRule(0.5, 0.5)))
      )
    )

    val actual = ValidationService.validate(parsedSchema)

    val expected = Valid(
      ChromoSchema(
        List(
          ChromoField("name", ChromoString, List(StringSetRule(Set("dave"), 1.0))),
          ChromoField("age", ChromoInt, List(RangeRule(20, 30, 1.0))),
          ChromoField("budget", ChromoDecimal, List(RangeRule(2000, 3000, 1.0))),
          ChromoField("married", ChromoBoolean, List(BooleanRule(0.5, 0.5)))
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

    val actual   = ValidationService.validate(parsedSchema)
    val expected = Invalid(ValidationError("Unknown type in field amazing_field: amazing_type"))

    assert(actual == expected)
  }

}
