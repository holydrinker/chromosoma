package holydrinker.chromosoma.parser

import holydrinker.chromosoma.model.{ BooleanRule, IntSetRule, RangeRule, StringSetRule }
import munit.FunSuite

class SchemaParserSuite extends FunSuite {

  test("parse simple and valid schema") {
    val path   = "src/test/resources/parsing/simple-valid-schema.json"
    val actual = SchemaParser.fromPath(path)
    val expected =
      ParsedChromoSchema(
        List(
          ParsedChromoField(
            "name",
            "string",
            List(
              StringSetRule(Set("dave", "simon"), 1.0)
            )
          ),
          ParsedChromoField(
            "age",
            "int",
            List(
              IntSetRule(Set(100), 0.1),
              RangeRule(10, 99, 0.9)
            )
          ),
          ParsedChromoField(
            "budget",
            "decimal",
            List(
              IntSetRule(Set(100), 0.5),
              RangeRule(1, 10, 0.5)
            )
          ),
          ParsedChromoField(
            "married",
            "boolean",
            List(
              BooleanRule(1.0, 0.0)
            )
          )
        )
      )

    assert(actual == Right(expected))
  }

  test("parse schema with not string datatype field".fail) {
    val path = getClass.getResource("/parsing/not-string-datatype-schema.json").toString
    SchemaParser.fromPath(path)
  }

  test("parse schema with missing required fields".fail) {
    val inputStream = getClass.getResource("/parsing/missing-required-field-schema.json").toString
    SchemaParser.fromPath(inputStream)
  }

}
