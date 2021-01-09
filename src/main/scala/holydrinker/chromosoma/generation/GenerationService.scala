package holydrinker.chromosoma.generation

import holydrinker.chromosoma.model.Rule

import scala.util.Random

/**
  * Rule-based generation engine.
  */
object GenerationService {

  /**
    * Generates a string.
    * @param rules generation rules
    * @return the string
    */
  def generateString(rules: List[Rule]): String =
    StringService.generate(rules)

  /**
    * Generate an integer.
    * @param rules generation rules
    * @return the integer
    */
  def generateInteger(rules: List[Rule]): Int =
    IntegerService.generate(rules)

  /**
    * Generate a decimal.
    * @param rules generation rules
    * @return the decimal
    */
  def generateDecimal(rules: List[Rule]): Double =
    DecimalService.generate(rules)

  /**
    * Generate a boolean.
    * @param rules generation rules
    * @return the boolean
    */
  def generateBoolean(rules: List[Rule]): Boolean =
    BooleanService.generate(rules)

}
