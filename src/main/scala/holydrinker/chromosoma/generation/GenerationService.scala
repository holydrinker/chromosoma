package holydrinker.chromosoma.generation

import holydrinker.chromosoma.logging.ChromoLogger
import holydrinker.chromosoma.model.{ BooleanRule, Rule }

import scala.util.Random

/**
  * Rule-based generation engine.
  */
object GenerationService extends ChromoLogger {

  /**
    * Generates a string.
    * @param rules generation rules
    * @return the string
    */
  def generateString(rules: List[Rule]): String = {
    val seed = Random.nextFloat()
    GenerationUtils.generateString(seed, rules)
  }

  /**
    * Generate an integer.
    * @param rules generation rules
    * @return the integer
    */
  def generateInteger(rules: List[Rule]): Int = {
    val seed = Random.nextFloat()
    GenerationUtils.generateDecimal(seed, rules).toInt
  }

  /**
    * Generate a decimal.
    * @param rules generation rules
    * @return the decimal
    */
  def generateDecimal(rules: List[Rule]): Double = {
    val seed = Random.nextFloat()
    GenerationUtils.generateDecimal(seed, rules)
  }

  /**
    * Generate a boolean.
    * @param rules generation rules
    * @return the boolean
    */
  def generateBoolean(rules: List[Rule]): Boolean = {
    if (rules.size > 1) {
      logWarn("Boolean rules cannot contain more than 1 rule. First rule was selected.")
    }
    val rule = getOrElseDefault(rules)
    val seed = Random.nextFloat()
    if (seed <= rule.`true`)
      true
    else
      false
  }

  private def getOrElseDefault[R <: Rule](rules: List[R]): BooleanRule = {
    val default = BooleanRule(`true` = 0.5, `false` = 0.5)

    rules.headOption match {
      case Some(rule: BooleanRule) =>
        rule
      case None =>
        default
    }
  }

}
