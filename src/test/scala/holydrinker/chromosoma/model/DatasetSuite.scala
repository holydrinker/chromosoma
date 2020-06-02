package holydrinker.chromosoma.model

import holydrinker.chromosoma.schema.{ ChromoBoolean, ChromoDecimal, ChromoInt, ChromoSchema, ChromoString, Field }
import munit.FunSuite

class DatasetSuite extends FunSuite {

  test("from single field schema") {
    val chromoSchema = ChromoSchema(
      Seq(
        Field("name", ChromoString),
        Field("age", ChromoInt),
        Field("budget", ChromoDecimal),
        Field("married", ChromoBoolean)
      )
    )
    val n = 1

    val result = Dataset.fromSchema(chromoSchema, n)

    assert(result.rows.head.get("name").isInstanceOf[String])
    assert(result.rows.head.get("age").isInstanceOf[Int])
    assert(result.rows.head.get("budget").isInstanceOf[Double])
    assert(result.rows.head.get("married").isInstanceOf[Boolean])
  }

}
