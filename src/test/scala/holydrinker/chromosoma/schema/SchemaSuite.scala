package holydrinker.chromosoma.schema

import org.scalatest.{ FlatSpec, Matchers }

class SchemaSuite extends FlatSpec with Matchers {

  it should "read trivial valid schema" in {
    val trivialSchemaPath  = "/trivialSchema.txt"
    val trivialSchemaInput = getClass.getResourceAsStream(trivialSchemaPath)
    val actual             = Schema.fromInputStream(trivialSchemaInput)
    val expected           = Right(Schema(Seq(Field("name", String), Field("surname", String))))
    assert(actual == expected)
  }

  it should "read trivial invalid schema" in {
    val trivialSchemaPath  = "/trivialInvalidSchema.txt"
    val trivialSchemaInput = getClass.getResourceAsStream(trivialSchemaPath)
    val actual             = Schema.fromInputStream(trivialSchemaInput)
    val expected           = Left("Invalid schema syntax: [a wrong field]")
    assert(actual == expected)
  }

}
