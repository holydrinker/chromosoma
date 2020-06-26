package holydrinker.chromosoma.parser

import holydrinker.chromosoma.model.{
  BooleanRule,
  ChromoBoolean,
  ChromoField,
  ChromoInt,
  ChromoSchema,
  ChromoString,
  IntSetRule,
  RangeRule,
  StringSetRule
}
import munit.FunSuite

class SemanticsServiceSuite extends FunSuite {

  test("Valid boolean fields") {
    val expected = ChromoSchema(
      List(
        ChromoField("name", ChromoBoolean, List(BooleanRule(0.5, 0.5))),
        ChromoField("name", ChromoBoolean, List(BooleanRule(1.0, 0.0))),
        ChromoField("name", ChromoBoolean, List(BooleanRule(0.001, 0.999)))
      )
    )

    val actual = SemanticsService.validateSchema(expected)

    assert(Right(expected) == actual)
  }

  test("Invalid boolean fields") {
    val schema = ChromoSchema(
      List(
        ChromoField("name", ChromoBoolean, List(BooleanRule(0.4, 0.5))),
        ChromoField("name", ChromoBoolean, List(BooleanRule(1.1, 0.0))),
        ChromoField("name", ChromoBoolean, List(BooleanRule(0.000, 0.999)))
      )
    )

    val actual = SemanticsService.validateSchema(schema)

    assert(actual.isLeft)
  }

  test("Valid string fields") {
    val schema = ChromoSchema(
      List(
        ChromoField(
          "dave_or_simon",
          ChromoString,
          List(
            StringSetRule(Set("dave, simon"), 1.0)
          )
        ),
        ChromoField(
          "more_dave_than_simon",
          ChromoString,
          List(
            StringSetRule(Set("dave"), 0.8),
            StringSetRule(Set("simon"), 0.2)
          )
        )
      )
    )

    val actual = SemanticsService.validateSchema(schema)

    assert(Right(schema) == actual)
  }

  test("String field with invalid range rule") {
    val schema = ChromoSchema(
      List(
        ChromoField(
          "fields_with_range_rule",
          ChromoString,
          List(
            StringSetRule(Set("dave, simon"), 0.5),
            RangeRule(1, 10, 0.5)
          )
        )
      )
    )

    val actual = SemanticsService.validateSchema(schema)

    assert(actual.isLeft)
  }

  test("String field with invalid rule distribution") {
    val schema = ChromoSchema(
      List(
        ChromoField(
          "fields_with_invalid_rule_distribution",
          ChromoString,
          List(
            StringSetRule(Set("dave"), 0.5),
            StringSetRule(Set("simon"), 0.6)
          )
        )
      )
    )

    val actual = SemanticsService.validateSchema(schema)

    assert(actual.isLeft)
  }

  test("Valid integer field") {
    val schema = ChromoSchema(
      List(
        ChromoField(
          "only_range_rule",
          ChromoInt,
          List(
            RangeRule(1, 10, 1.0)
          )
        ),
        ChromoField(
          "only_set_rule",
          ChromoInt,
          List(
            IntSetRule(Set(1), 1.0)
          )
        ),
        ChromoField(
          "range_and_set_together",
          ChromoInt,
          List(
            RangeRule(1, 10, 0.5),
            IntSetRule(Set(1), 0.5)
          )
        )
      )
    )

    val actual = SemanticsService.validateSchema(schema)

    assert(Right(schema) == actual)
  }

  test("Integer field with invalid rule distribution") {
    val schema = ChromoSchema(
      List(
        ChromoField(
          "range_and_set_together",
          ChromoInt,
          List(
            RangeRule(1, 10, 0.5),
            IntSetRule(Set(1), 0.6)
          )
        )
      )
    )

    val actual = SemanticsService.validateSchema(schema)

    assert(actual.isLeft)
  }

  test("Integer field with invalid rule type") {
    val schema = ChromoSchema(
      List(
        ChromoField(
          "range_and_set_together",
          ChromoInt,
          List(
            StringSetRule(Set("dave"), 1.0)
          )
        )
      )
    )

    val actual = SemanticsService.validateSchema(schema)

    assert(actual.isLeft)
  }

}
