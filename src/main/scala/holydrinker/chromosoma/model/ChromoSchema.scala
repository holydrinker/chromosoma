package holydrinker.chromosoma.model

import java.io.{ FileInputStream, InputStream }

import holydrinker.chromosoma.model
import holydrinker.chromosoma.parser.SchemaParser
import org.apache.avro.Schema

case class ChromoField(name: String, dataType: ChromoType, rules: List[Rule] = List.empty[Rule])

case class ChromoSchema(fields: Seq[ChromoField])

object ChromoSchema {

  def fromJson(path: String): ChromoSchema =
    // val jsonInputStream = new FileInputStream(path)
    // SchemaParser.fromPath(jsonInputStream)
    null

  def toAvroSchema(chromoSchema: ChromoSchema): Schema = {
    val fieldTemplate = "{\"name\": \"%s\", \"type\": \"%s\"}"
    val fields = chromoSchema.fields
      .map {
        case ChromoField(name, ChromoString, _) =>
          fieldTemplate.format(name, "string")
        case ChromoField(name, ChromoDecimal, _) =>
          fieldTemplate.format(name, "double")
        case ChromoField(name, ChromoInt, _) =>
          fieldTemplate.format(name, "int")
        case ChromoField(name, ChromoBoolean, _) =>
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
