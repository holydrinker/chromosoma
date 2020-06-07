package holydrinker.chromosoma.model

import holydrinker.chromosoma.schema.{
  ChromoBoolean,
  ChromoDecimal,
  ChromoField,
  ChromoInt,
  ChromoSchema,
  ChromoString
}
import munit.FunSuite

class DatasetSuite extends FunSuite {

  test("from single field schema") {
    val chromoSchema = ChromoSchema(
      Seq(
        ChromoField("name", ChromoString),
        ChromoField("age", ChromoInt),
        ChromoField("budget", ChromoDecimal),
        ChromoField("married", ChromoBoolean)
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
