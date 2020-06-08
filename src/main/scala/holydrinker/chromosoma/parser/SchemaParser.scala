package holydrinker.chromosoma.parser

import java.nio.charset.StandardCharsets
import java.io.{ InputStream, StringWriter }

import spray.json._
import DefaultJsonProtocol._
import holydrinker.chromosoma.model.{
  ChromoBoolean,
  ChromoDecimal,
  ChromoField,
  ChromoInt,
  ChromoSchema,
  ChromoString,
  ChromoType,
  DistributionValue,
  IntSetRule,
  RangeRule,
  Rule
}
import org.apache.commons.io.IOUtils

object SchemaParser {

  implicit val chromoTypeFormat = new JsonFormat[ChromoType] {
    override def read(json: JsValue): ChromoType = json match {
      case JsString("string")        => ChromoString
      case JsString("int")           => ChromoInt
      case JsString("boolean")       => ChromoBoolean
      case JsString("decimal")       => ChromoDecimal
      case JsString(unsupportedType) => throw new RuntimeException(s"Unsupported type: $unsupportedType")
      case _                         => throw new RuntimeException("All dataType fields in schema definition should be string.")
    }

    override def write(obj: ChromoType): JsValue = obj match {
      case ChromoString  => JsString("string")
      case ChromoInt     => JsString("int")
      case ChromoBoolean => JsString("boolean")
      case ChromoDecimal => JsString("decimal")
    }
  }

  implicit val rule = new JsonFormat[Rule] {
    override def read(json: JsValue): Rule = json match {
      case JsObject(fields) =>
        fields.getOrElse("type", "undefined") match {
          case JsString("set")   => makeSetRule(fields)
          case JsString("range") => makeRangeRule(fields)
        }
    }

    private def makeSetRule(fields: Map[String, JsValue]): IntSetRule = {
      val setValues = fields.get("values").get match {
        case JsArray(values) =>
          values.map {
            case JsNumber(number) => number.toInt
          }
      }

      val distributionValue = fields.get("distribution").get match {
        case JsNumber(value) => value.toDouble
      }

      IntSetRule(setValues.toSet, DistributionValue(distributionValue))
    }

    private def makeRangeRule(fields: Map[String, JsValue]): RangeRule = {
      val min = fields.get("min").get match {
        case JsNumber(minValue) => minValue.toInt
      }

      val max = fields.get("max").get match {
        case JsNumber(maxValue) => maxValue.toInt
      }

      val distribution = fields.get("distribution").get match {
        case JsNumber(distributionValue) => distributionValue.toDouble
      }

      RangeRule(Range(min, max), DistributionValue(distribution))
    }

    override def write(obj: Rule): JsValue = JsNumber(10)
  }

  def fromInputStream(inputStream: InputStream): ChromoSchema = {
    val writer = new StringWriter()
    IOUtils.copy(inputStream, writer, StandardCharsets.UTF_8)
    val source = writer.toString.parseJson

    implicit val chromoFieldFormat = jsonFormat3(ChromoField)
    implicit val configFormat      = jsonFormat1(ChromoSchema.apply)
    source.convertTo[ChromoSchema]
  }

  def hasValidDistribution(schema: ChromoSchema): Boolean = {
    val overallDistribution = schema.fields.map(sumRuleDistributionInField)
    overallDistribution == 0.0 || overallDistribution == 1.0
  }

  private def sumRuleDistributionInField(field: ChromoField): Double =
    field.rules.map(_.distribution).sum

}
