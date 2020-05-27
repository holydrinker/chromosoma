package holydrinker.chromosoma.schema

import munit.FunSuite

class SchemaSuite extends FunSuite {

  test("read trivial valid schema") {
    val trivialSchemaPath  = "/trivialSchema.txt"
    val trivialSchemaInput = getClass.getResourceAsStream(trivialSchemaPath)
    val actual             = Schema.fromInputStream(trivialSchemaInput)
    val expected           = Right(Schema(Seq(Field("name", String), Field("surname", String))))
    assert(actual == expected)
  }

  test("read trivial invalid schema") {
    val trivialSchemaPath  = "/trivialInvalidSchema.txt"
    val trivialSchemaInput = getClass.getResourceAsStream(trivialSchemaPath)
    val actual             = Schema.fromInputStream(trivialSchemaInput)
    val expected           = Left("Invalid schema syntax: [a wrong field]")
    assert(actual == expected)
  }

}
