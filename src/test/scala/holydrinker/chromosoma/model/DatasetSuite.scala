package holydrinker.chromosoma.model

import munit.FunSuite

class DatasetSuite extends FunSuite {

  test("simple schema with one range rule") {
    val fields = List(
      ChromoField(
        "name",
        ChromoString,
        List(
          StringSetRule(Set("dave", "simone"), 1.0)
        )
      ),
      ChromoField(
        "age",
        ChromoInt,
        List(
          RangeRule(0, 10, 1.0)
        )
      ),
      ChromoField(
        "budget",
        ChromoDecimal,
        List(
          RangeRule(0, 10, 1.0)
        )
      ),
      ChromoField(
        "married",
        ChromoBoolean,
        List(
          BooleanRule(`true` = 1, `false` = 0)
        )
      )
    )

    val schema = ChromoSchema(fields)
    val n      = 1

    val result = Dataset.fromSchema(schema, n).right.get

    val age     = result.rows.head.get("age").asInstanceOf[Int]
    val budget  = result.rows.head.get("budget").asInstanceOf[Double]
    val married = result.rows.head.get("married").asInstanceOf[Boolean]

    assert(age >= 0 && age <= 10)
    assert(budget >= 0 && budget <= 10)
    assert(married)
  }

}
