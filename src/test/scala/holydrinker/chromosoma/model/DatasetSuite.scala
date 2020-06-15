package holydrinker.chromosoma.model

import munit.FunSuite

class DatasetSuite extends FunSuite {

  test("simple schema with one range rule") {
    val fields = Seq(
      ChromoField("name", ChromoString),
      ChromoField(
        "age",
        ChromoInt,
        List(
          RangeRule(Range(0, 10), DistributionValue(1.0))
        )
      ),
      ChromoField(
        "budget",
        ChromoDecimal,
        List(
          RangeRule(Range(0, 10), DistributionValue(1.0))
        )
      ),
      ChromoField("married", ChromoBoolean)
    )

    val schema = ChromoSchema(fields)
    val n      = 1

    val result = Dataset.fromSchema(schema, n)

    val age    = result.rows.head.get("age").asInstanceOf[Int]
    val budget = result.rows.head.get("bedget").asInstanceOf[Double]
    assert(age >= 0 && age <= 10)
    assert(budget >= 0 && budget <= 10)
    assert(result.rows.head.get("name").isInstanceOf[String])
    assert(result.rows.head.get("budget").isInstanceOf[Double])
    assert(result.rows.head.get("married").isInstanceOf[Boolean])
  }

}
