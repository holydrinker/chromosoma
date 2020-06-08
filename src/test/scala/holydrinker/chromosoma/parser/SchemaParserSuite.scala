package holydrinker.chromosoma.parser

import holydrinker.chromosoma.model.{
  ChromoField,
  ChromoInt,
  ChromoSchema,
  ChromoString,
  DistributionValue,
  IntSetRule,
  RangeRule
}
import munit.FunSuite

class SchemaParserSuite extends FunSuite {

  test("parse simple and valid schema") {
    val inputStream = getClass.getResourceAsStream("/parsing/simple-valid-schema.json")
    val actual      = SchemaParser.fromInputStream(inputStream)
    val expected =
      ChromoSchema(
        Seq(
          ChromoField("name", ChromoString, List()),
          ChromoField(
            "age",
            ChromoInt,
            List(
              IntSetRule(Set(100), DistributionValue(0.1)),
              RangeRule(Range(10, 99), DistributionValue(0.9))
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
