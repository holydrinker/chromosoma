package holydrinker.chromosoma.model

import java.io.InputStream

import holydrinker.chromosoma.model
import holydrinker.chromosoma.validation.SchemaParser
import org.apache.avro.Schema

case class ChromoField(name: String, dataType: ChromoType)

case class RowFields(name: String, datatype: String) {
  def validate(): Either[String, ChromoField] =
    datatype match {
      case "string"  => Right(ChromoField(name, ChromoString))
      case "decimal" => Right(ChromoField(name, ChromoDecimal))
      case "int"     => Right(ChromoField(name, ChromoInt))
      case "boolean" => Right(ChromoField(name, ChromoBoolean))
      case _         => Left(name)
    }
}

case class ChromoSchema(fields: Seq[ChromoField] = Seq.empty[ChromoField], rules: List[Rule] = List.empty[Rule]) {
  def addRule(rule: Rule): ChromoSchema =
    ChromoSchema(fields, rules ::: List(rule))
}

object ChromoSchema {

  def fromInputStream(input: InputStream): Either[String, ChromoSchema] =
    for {
      rowFields <- SchemaParser.extractRowFieldsFromStream(input)
      fields    <- SchemaParser.validateFields(rowFields)
    } yield model.ChromoSchema(fields)

  def toAvroSchema(chromoSchema: ChromoSchema): Schema = {
    val fieldTemplate = "{\"name\": \"%s\", \"type\": \"%s\"}"
    val fields = chromoSchema.fields
      .map {
        case ChromoField(name, ChromoString) =>
          fieldTemplate.format(name, "string")
        case ChromoField(name, ChromoDecimal) =>
          fieldTemplate.format(name, "double")
        case ChromoField(name, ChromoInt) =>
          fieldTemplate.format(name, "int")
        case ChromoField(name, ChromoBoolean) =>
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
