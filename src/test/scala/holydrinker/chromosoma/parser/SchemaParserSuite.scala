package holydrinker.chromosoma.parser

import holydrinker.chromosoma.model.{
  BooleanRule,
  ChromoBoolean,
  ChromoDecimal,
  ChromoField,
  ChromoInt,
  ChromoSchema,
  ChromoString,
  DistributionValue,
  IntSetRule,
  RangeRule,
  StringSetRule
}
import munit.FunSuite

class SchemaParserSuite extends FunSuite {

  test("parse simple and valid schema") {
    val inputStream = getClass.getResourceAsStream("/parsing/simple-valid-schema.json")
    val actual      = SchemaParser.fromInputStream(inputStream)
    val expected =
      ChromoSchema(
        Seq(
          ChromoField(
            "name",
            ChromoString,
            List(
              StringSetRule(Set("dave", "simon"), DistributionValue(1.0))
            )
          ),
          ChromoField(
            "age",
            ChromoInt,
            List(
              IntSetRule(Set(100), DistributionValue(0.1)),
              RangeRule(Range(10, 99), DistributionValue(0.9))
            )
          ),
          ChromoField(
            "budget",
            ChromoDecimal,
            List(
              IntSetRule(Set(100), DistributionValue(0.5)),
              RangeRule(Range(1, 10), DistributionValue(0.5))
            )
          ),
          ChromoField(
            "married",
            ChromoBoolean,
            List(
              BooleanRule(DistributionValue(1.0), DistributionValue(0.0))
            )
          )
        )
      )

    assert(actual == expected)
  }

  test("parse schema with not string datatype field".fail) {
    val inputStream = getClass.getResourceAsStream("/parsing/not-string-datatype-schema.json")
    SchemaParser.fromInputStream(inputStream)
  }

  test("parse schema with missing required fields".fail) {
    val inputStream = getClass.getResourceAsStream("/parsing/missing-required-field-schema.json")
    SchemaParser.fromInputStream(inputStream)
  }

}
