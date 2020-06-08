package holydrinker.chromosoma.parser

import java.nio.charset.StandardCharsets

import munit.FunSuite
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

import spray.json._
import DefaultJsonProtocol._

case class Config(fields: List[ChromoField])

class NewSchemaParserSuite extends FunSuite {

  case class NewChromoField(name: String, dataType: ChromoType, rules: List[Rule])

  case class NewConfig(fields: List[NewChromoField])

  test("spray json") {

    implicit val chromoTypeFormat = new JsonFormat[ChromoType] {
      override def read(json: JsValue): ChromoType = json match {
        case JsString("string")  => ChromoString
        case JsString("int")     => ChromoInt
        case JsString("boolean") => ChromoBoolean
        case JsString("decimal") => ChromoDecimal
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

    val sourceStream               = getClass.getResourceAsStream("/newschema.json")
    val source                     = new String(sourceStream.readAllBytes(), StandardCharsets.UTF_8).parseJson
    implicit val chromoFieldFormat = jsonFormat3(NewChromoField)
    implicit val configFormat      = jsonFormat1(NewConfig)
    val config                     = source.convertTo[NewConfig]
    println(config)
  }
  /*
  case class Rule(bounds: List[Int])

  case class InnerRule(range: Range)



  test("pure config") {
    implicit val ruleReader = ConfigReader[Rule].map {
      case Rule(bounds) =>
        assert(bounds.size == 2)
        InnerRule(Range(bounds(0), bounds(1)))
    }

    val configPath = getClass().getResource("/application.conf").getPath
    val config     = ConfigSource.file(configPath).load[NewConfig]
    println(config)
  }
 */

}
