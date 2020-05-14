import holydrinker.generator.core.{ DataTypeString, Field, RowFields, SchemaValidationService }
import org.scalatest._

class SchemaSuite extends FlatSpec with Matchers {

  it should "validate a trivial schema" in {
    val trivialSchemaPath  = "trivialSchema.txt"
    val trivialSchemaInput = getClass.getResourceAsStream(trivialSchemaPath)
    val actual             = SchemaValidationService.extractRowFieldsFromStream(trivialSchemaInput)
    val expected           = Right(Seq(RowFields("name", "string"), RowFields("surname", "string")))
    assert(actual == expected)
  }

  it should "not validate a trivial invalid schema" in {
    val trivialSchemaPath  = "trivialInvalidSchema.txt"
    val trivialSchemaInput = getClass.getResourceAsStream(trivialSchemaPath)
    val actual             = SchemaValidationService.extractRowFieldsFromStream(trivialSchemaInput)
    val expected           = Left("Invalid schema syntax: [a wrong field]")
    assert(actual == expected)
  }

  it should "validate fields with string datatype" in {
    val stringFields = Seq(RowFields("name", "string"), RowFields("surname", "string"))

    val actual = SchemaValidationService.validateFields(stringFields)

    val expected = Right(Seq(Field("name", DataTypeString), Field("surname", DataTypeString)))

    assert(actual == expected)
  }

  it should "not validate fields with invalid datatype" in {
    val stringFields = Seq(RowFields("name", "something-amazing"), RowFields("surname", "best-surname-ever"))

    val actual   = SchemaValidationService.validateFields(stringFields)
    val expected = Left("Invalid datatype in fields: [name,surname]")
    assert(actual == expected)
  }

}
