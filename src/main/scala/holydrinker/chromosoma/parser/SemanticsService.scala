package holydrinker.chromosoma.parser

import holydrinker.chromosoma.model._
import cats.implicits._
import holydrinker.chromosoma.error.{ ChromoError, ChromoMessages }

/**
  * Exposes services to validate the body of a [[ChromoSchema]]
  */
object SemanticsService {

  /**
    * Validates the schema's body.
    * For example you cannot decalre range rule with only one value, you cannot decalre a boolean rule
    * where the distribution of true + the distribution of false is > 1, and so on.
    *
    * @param schema the schema to be validated
    * @return the same schema in input if is valid
    */
  def validateSchema(schema: ChromoSchema): Either[ChromoError, ChromoSchema] =
    schema.fields
      .traverse(validateSingleFieldSemantics)
      .map(ChromoSchema(_))

  private def validateSingleFieldSemantics(field: ChromoField): Either[ChromoError, ChromoField] =
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

  private def validateBooleanField(field: ChromoField): Either[ChromoError, ChromoField] = field match {
    case ChromoField(name, ChromoBoolean, rules) =>
      rules match {
        case BooleanRule(trueDist, falseDist) :: Nil if validBooleanRuleDistribution(trueDist, falseDist) =>
          Right(field)
        case _ =>
          Left(ChromoError(ChromoMessages.invalidBooleanRule(name)))
      }

    case ChromoField(name, _, _) =>
      Left(ChromoError(ChromoMessages.cannotValidate(name)))
  }

  private def validBooleanRuleDistribution(trueDistribution: Double, falseDistribution: Double): Boolean =
    (BigDecimal(trueDistribution) + BigDecimal(falseDistribution)) == BigDecimal(1.0)

  private def validateStringField(field: ChromoField): Either[ChromoError, ChromoField] = field match {
    case ChromoField(name, ChromoString, rules) if rules.filter(_.isInstanceOf[RangeRule]).size > 0 =>
      Left(ChromoError(ChromoMessages.stringFieldCannotContainsRangeRule(name.toUpperCase)))

    case ChromoField(_, ChromoString, _) =>
      validateRuleDistribution(field)
  }

  private def validateNumericField(field: ChromoField): Either[ChromoError, ChromoField] = {
    assert(field.dataType.isInstanceOf[ChromoInt.type] || field.dataType.isInstanceOf[ChromoDecimal.type])

    val invalidRules = field.rules
      .filter(!_.isInstanceOf[RangeRule])
      .filter(!_.isInstanceOf[IntSetRule])

    if (invalidRules.size > 0) {
      Left(ChromoError(ChromoMessages.integerFieldMustContainRangeRule(field.name)))
    } else {
      validateRuleDistribution(field)
    }
  }

  private def validateRuleDistribution(field: ChromoField): Either[ChromoError, ChromoField] = {
    val overallDistribution = field.rules.foldLeft(BigDecimal(0.0))((acc, rule) => acc + BigDecimal(rule.distribution))
    if (overallDistribution != 1.0)
      Left(
        ChromoError(ChromoMessages.wrongDistribution(field.name.toUpperCase, overallDistribution))
      )
    else
      Right(field)
  }
}
