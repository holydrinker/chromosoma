package holydrinker.chromosoma.parser

import holydrinker.chromosoma.model._
import cats.implicits._

object SemanticsService {

  def validateSchema(schema: ChromoSchema): Either[String, ChromoSchema] =
    schema.fields
      .traverse(validateSingleFieldSemantics)
      .map(ChromoSchema(_))

  private def validateSingleFieldSemantics(field: ChromoField): Either[String, ChromoField] =
    field.dataType match {
      case ChromoBoolean =>
        validateBooleanField(field)
      case ChromoString =>
        validateStringField(field)
      case ChromoInt =>
        validateNumericField(field)
      case ChromoDecimal =>
        validateNumericField(field)
    }

  private def validateBooleanField(field: ChromoField): Either[String, ChromoField] = field match {
    case ChromoField(name, ChromoBoolean, rules) =>
      rules match {
        case BooleanRule(trueDist, falseDist) :: Nil if validBooleanRuleDistribution(trueDist, falseDist) =>
          Right(field)
        case _ =>
          Left(
            s"Invalid boolean rule for field $name. be sure you have exactly one rule and that the overall distribution is equal to 1.0"
          )
      }

    case ChromoField(name, _, _) =>
      Left(s"Cannot validate field $name")
  }

  private def validBooleanRuleDistribution(trueDistribution: Double, falseDistribution: Double): Boolean =
    (BigDecimal(trueDistribution) + BigDecimal(falseDistribution)) == BigDecimal(1.0)

  private def validateStringField(field: ChromoField): Either[String, ChromoField] = field match {
    case ChromoField(name, ChromoString, rules) if rules.filter(_.isInstanceOf[RangeRule]).size > 0 =>
      Left(s"String field ${name.toUpperCase} cannot contains range rules.")

    case ChromoField(_, ChromoString, _) =>
      validateRuleDistribution(field)
  }

  private def validateNumericField(field: ChromoField): Either[String, ChromoField] = {
    assert(field.dataType.isInstanceOf[ChromoInt.type] || field.dataType.isInstanceOf[ChromoDecimal.type])

    val invalidRules = field.rules
      .filter(!_.isInstanceOf[RangeRule])
      .filter(!_.isInstanceOf[IntSetRule])

    if (invalidRules.size > 0) {
      Left(s"Integer field ${field.name.toUpperCase} must contain only RangeRule and IntSetRule")
    } else {
      validateRuleDistribution(field)
    }
  }

  private def validateRuleDistribution(field: ChromoField): Either[String, ChromoField] = {
    val overallDistribution = field.rules.foldLeft(BigDecimal(0.0))((acc, rule) => acc + BigDecimal(rule.distribution))
    if (overallDistribution != 1.0)
      Left(
        s"String field ${field.name.toUpperCase} has overallDistribution = $overallDistribution. It shoud be equal to 1.0."
      )
    else
      Right(field)
  }
}
