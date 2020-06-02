package holydrinker.chromosoma.schema

import munit.FunSuite
import org.apache.avro.Schema

class ChromoSchemaSuite extends FunSuite {

  test("read trivial valid schema") {
    val trivialSchemaPath  = "/trivialSchema.txt"
    val trivialSchemaInput = getClass.getResourceAsStream(trivialSchemaPath)
    val actual             = ChromoSchema.fromInputStream(trivialSchemaInput)
    val expected           = Right(ChromoSchema(Seq(Field("name", String), Field("surname", String))))
    assert(actual == expected)
  }

  test("read trivial invalid schema") {
    val trivialSchemaPath  = "/trivialInvalidSchema.txt"
    val trivialSchemaInput = getClass.getResourceAsStream(trivialSchemaPath)
    val actual             = ChromoSchema.fromInputStream(trivialSchemaInput)
    val expected           = Left("Invalid schema syntax: [a wrong field]")
    assert(actual == expected)
  }

  test("convert to avro schema") {
    val chromoSchema = ChromoSchema(
      Seq(
        Field("name", String),
        Field("age", Int),
        Field("budget", Decimal),
        Field("married", Boolean)
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