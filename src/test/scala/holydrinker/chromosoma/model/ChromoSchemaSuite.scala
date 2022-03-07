package holydrinker.chromosoma.model

import cats.data.Validated.Valid
import holydrinker.chromosoma.parser.ParsedChromoField
import munit.FunSuite
import org.apache.avro.Schema

class ChromoSchemaSuite extends FunSuite {

  test("Make simple and valid schema") {
    val parsedFields =
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

    val expected =
      ChromoSchema(
        List(
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

    val actual = ChromoSchema.fromFields(parsedFields)

    assert(actual == Valid(expected))
  }

  test("convert to avro schema") {
    val chromoSchema = ChromoSchema(
      List(
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

//    val result = ChromoSchema.toAvroSchema(chromoSchema)
//
//    assert(expected == result)
  }

}
