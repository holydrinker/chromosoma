package holydrinker.chromosoma.schema

import java.io.InputStream
import holydrinker.chromosoma.validation.SchemaValidationService
import org.apache.avro.Schema

case class Field(name: String, dataType: ChromoType)

case class RowFields(name: String, datatype: String) {
  def validate(): Either[String, Field] =
    datatype match {
      case "string"  => Right(Field(name, ChromoString))
      case "decimal" => Right(Field(name, ChromoDecimal))
      case "int"     => Right(Field(name, ChromoInt))
      case "boolean" => Right(Field(name, ChromoBoolean))
      case _         => Left(name)
    }
}

case class ChromoSchema(fields: Seq[Field] = Seq.empty[Field])

object ChromoSchema {

  def fromInputStream(input: InputStream): Either[String, ChromoSchema] =
    for {
      rowFields <- SchemaValidationService.extractRowFieldsFromStream(input)
      fields    <- SchemaValidationService.validateFields(rowFields)
    } yield ChromoSchema(fields)

  def toAvroSchema(chromoSchema: ChromoSchema): Schema = {
    val fieldTemplate = "{\"name\": \"%s\", \"type\": \"%s\"}"
    val fields = chromoSchema.fields
      .map {
        case Field(name, ChromoString) =>
          fieldTemplate.format(name, "string")
        case Field(name, ChromoDecimal) =>
          fieldTemplate.format(name, "double")
        case Field(name, ChromoInt) =>
          fieldTemplate.format(name, "int")
        case Field(name, ChromoBoolean) =>
          fieldTemplate.format(name, "boolean")
      }
      .mkString(", ")

    val rowSchema =
      s"""{
        |"type": "record", 
        |"name": "chromorecord", 
        |"fields": [$fields]
        |}""".stripMargin

    new Schema.Parser().parse(rowSchema)
  }

}
