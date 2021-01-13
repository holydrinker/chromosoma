package holydrinker.chromosoma.parser

import cats.data.Validated.Valid
import holydrinker.chromosoma.model.{ BooleanRule, IntSetRule, RangeRule, StringSetRule }
import munit.FunSuite

class ParsingServiceSuite extends FunSuite {

  test("parse simple and valid schema") {
    val path   = "src/test/resources/parsing/simple-valid-schema.json"
    val actual = ParsingService.fromPath(path)
    val fields =
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

    val expected = Dna(10, "result", "csv", fields)
    assert(actual == Valid(expected))
  }

  test("parse schema with not string datatype field") {
    val path   = getClass.getResource("/parsing/not-string-datatype-schema.json").getPath
    val result = ParsingService.fromPath(path)
    assert(result.isInvalid)
  }

  test("parse schema with missing required fields") {
    val inputStream = getClass.getResource("/parsing/missing-required-field-schema.json").getPath
    val result      = ParsingService.fromPath(inputStream)
    assert(result.isInvalid)
  }

  test("parse no sense schema") {
    val inputStream = getClass.getResource("/parsing/no-sense-schema.json").getPath
    val result      = ParsingService.fromPath(inputStream)
    assert(result.isInvalid)
  }

}
