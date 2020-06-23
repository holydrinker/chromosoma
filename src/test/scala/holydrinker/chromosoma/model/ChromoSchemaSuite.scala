package holydrinker.chromosoma.model

import munit.FunSuite
import org.apache.avro.Schema

class ChromoSchemaSuite extends FunSuite {

  test("Make simple and valid schema") {
    val jsonPath = "src/test/resources/parsing/simple-valid-schema.json"
    val actual   = ChromoSchema.fromJson(jsonPath)

    val expected =
      ChromoSchema(
        Seq(
          ChromoField(
            "name",
            ChromoString,
            List(
              StringSetRule(Set("dave", "simon"), 1.0)
            )
          ),
          ChromoField(
            "age",
            ChromoInt,
            List(
              IntSetRule(Set(100), 0.1),
              RangeRule(10, 99, 0.9)
            )
          ),
          ChromoField(
            "budget",
            ChromoDecimal,
            List(
              IntSetRule(Set(100), 0.5),
              RangeRule(1, 10, 0.5)
            )
          ),
          ChromoField(
            "married",
            ChromoBoolean,
            List(
              BooleanRule(1.0, 0.0)
            )
          )
        )
      )
    assert(actual == expected)
  }

  test("Fail in making schema with not string datatype field".fail) {
    val jsonPath = getClass.getResource("/parsing/not-string-datatype-schema.json").toString
    ChromoSchema.fromJson(jsonPath)
  }

  test("Fail in making schema with missing required fields".fail) {
    val jsonPath = getClass.getResource("/parsing/missing-required-field-schema.json").toString
    ChromoSchema.fromJson(jsonPath)
  }

  test("convert to avro schema") {
    val chromoSchema = ChromoSchema(
      Seq(
        ChromoField("name", ChromoString),
        ChromoField("age", ChromoInt),
        ChromoField("budget", ChromoDecimal),
        ChromoField("married", ChromoBoolean)
      )
    )

    val expectedStringSchema =
      """
        |{
        |"type": "record", 
        |"name": "chromorecord", 
        |"fields": [
        |{"name": "name", "type": "string"},
        |{"name": "age", "type": "int"},
        |{"name": "budget", "type": "double"},
        |{"name": "married", "type": "boolean"}
        |]}
        |""".stripMargin

    val expected = new Schema.Parser().parse(expectedStringSchema)

    val result = ChromoSchema.toAvroSchema(chromoSchema)

    assert(expected == result)
  }

}
