package holydrinker.chromosoma.model

import holydrinker.chromosoma.schema.{ Boolean, ChromoSchema, Decimal, Field, Int, String }
import munit.FunSuite

class DatasetSuite extends FunSuite {

  test("from single field schema") {
    val chromoSchema = ChromoSchema(
      Seq(
        Field("name", String),
        Field("age", Int),
        Field("budget", Decimal),
        Field("married", Boolean)
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
