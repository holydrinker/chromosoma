package holydrinker.chromosoma.schema

import java.io.InputStream
import holydrinker.chromosoma.validation.SchemaValidationService

case class Field(name: String, dataType: DataType)

case class RowFields(name: String, datatype: String) {
  def validate(): Either[String, Field] =
    datatype match {
      case "string"  => Right(Field(name, String))
      case "decimal" => Right(Field(name, Decimal))
      case "int"     => Right(Field(name, Int))
      case "boolean" => Right(Field(name, Boolean))
      case _         => Left(name)
    }
}

case class Schema(fields: Seq[Field] = Seq.empty[Field])

object Schema {

  def fromInputStream(input: InputStream): Either[String, Schema] =
    for {
      rowFields <- SchemaValidationService.extractRowFieldsFromStream(input)
      fields    <- SchemaValidationService.validateFields(rowFields)
    } yield Schema(fields)

}
