package holydrinker.chromosoma.validation

import holydrinker.chromosoma.schema.{ Field, RowFields, String }
import munit.FunSuite

class SchemaValidationServiceSuite extends FunSuite {

  test("validate a trivial schema") {
    val trivialSchemaPath  = "/trivialSchema.txt"
    val trivialSchemaInput = getClass.getResourceAsStream(trivialSchemaPath)
    val actual             = SchemaValidationService.extractRowFieldsFromStream(trivialSchemaInput)
    val expected           = Right(Seq(RowFields("name", "string"), RowFields("surname", "string")))
    assert(actual == expected)
  }

  test("not validate a trivial invalid schema") {
    val trivialSchemaPath  = "/trivialInvalidSchema.txt"
    val trivialSchemaInput = getClass.getResourceAsStream(trivialSchemaPath)
    val actual             = SchemaValidationService.extractRowFieldsFromStream(trivialSchemaInput)
    val expected           = Left("Invalid schema syntax: [a wrong field]")
    assert(actual == expected)
  }

  test("validate fields with string datatype") {
    val stringFields = Seq(RowFields("name", "string"), RowFields("surname", "string"))

    val actual = SchemaValidationService.validateFields(stringFields)

    val expected = Right(Seq(Field("name", String), Field("surname", String)))

    assert(actual == expected)
  }

  test("not validate fields with invalid datatype") {
    val stringFields = Seq(RowFields("name", "something-amazing"), RowFields("surname", "best-surname-ever"))

    val actual   = SchemaValidationService.validateFields(stringFields)
    val expected = Left("Invalid datatype in fields: [name,surname]")
    assert(actual == expected)
  }

}
