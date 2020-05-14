package holydrinker.generator.core

import java.io.InputStream

case class Field(name: String, dataType: DataType)

case class RowFields(name: String, datatype: String) {
  def validate(): Either[String, Field] =
    datatype match {
      case "string"  => Right(Field(name, DataTypeString))
      case "decimal" => Right(Field(name, DataTypeDecimal))
      case "int"     => Right(Field(name, DataTypeInt))
      case "boolean" => Right(Field(name, DataTypeBoolean))
      case _         => Left(name)
    }
}

case class Schema(fields: Seq[Field] = Seq.empty[Field])

object Schema {

  def fromInputStream(input: InputStream): Schema = {
    val rowFields = SchemaValidationService.extractRowFieldsFromStream(input) match {
      case Right(rowFields) =>
        rowFields
      case Left(errorMessage) =>
        throw new Exception(errorMessage)
    }

    val fields = SchemaValidationService.validateFields(rowFields) match {
      case Right(validatedFields) =>
        validatedFields
      case Left(errorMessage) =>
        throw new Exception(errorMessage)
    }

    Schema(fields)
  }

}
