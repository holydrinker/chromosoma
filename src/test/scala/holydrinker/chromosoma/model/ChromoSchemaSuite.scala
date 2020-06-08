package holydrinker.chromosoma.model

import holydrinker.chromosoma.parser.SchemaParser
import munit.FunSuite
import org.apache.avro.Schema

class ChromoSchemaSuite extends FunSuite {

  test("Make simple and valid schema") {
    val jsonPath = "src/test/resources/parsing/simple-valid-schema.json"
    val actual   = ChromoSchema.fromJson(jsonPath)

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
