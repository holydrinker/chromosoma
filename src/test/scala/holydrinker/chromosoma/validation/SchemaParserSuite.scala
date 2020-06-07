package holydrinker.chromosoma.validation

import holydrinker.chromosoma.schema.{ ChromoField, ChromoString, RowFields }
import munit.FunSuite

class SchemaParserSuite extends FunSuite {

  test("validate a trivial schema") {
    val trivialSchemaPath  = "/trivialSchema.txt"
    val trivialSchemaInput = getClass.getResourceAsStream(trivialSchemaPath)
    val actual             = SchemaParser.extractRowFieldsFromStream(trivialSchemaInput)
    val expected           = Right(Seq(RowFields("name", "string"), RowFields("surname", "string")))
    assert(actual == expected)
  }

  test("not validate a trivial invalid schema") {
    val trivialSchemaPath  = "/trivialInvalidSchema.txt"
    val trivialSchemaInput = getClass.getResourceAsStream(trivialSchemaPath)
    val actual             = SchemaParser.extractRowFieldsFromStream(trivialSchemaInput)
    val expected           = Left("Invalid schema syntax: [a wrong field]")
    assert(actual == expected)
  }

  test("validate fields with string datatype") {
    val stringFields = Seq(RowFields("name", "string"), RowFields("surname", "string"))

    val actual = SchemaParser.validateFields(stringFields)

    val expected = Right(Seq(ChromoField("name", ChromoString), ChromoField("surname", ChromoString)))

    assert(actual == expected)
  }

  test("not validate fields with invalid datatype") {
    val stringFields = Seq(RowFields("name", "something-amazing"), RowFields("surname", "best-surname-ever"))

    val actual   = SchemaParser.validateFields(stringFields)
    val expected = Left("Invalid datatype in fields: [name,surname]")
    assert(actual == expected)
  }

}
