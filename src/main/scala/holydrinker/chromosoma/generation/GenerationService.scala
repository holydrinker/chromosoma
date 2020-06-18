package holydrinker.chromosoma.generation

import holydrinker.chromosoma.model.Rule

import scala.util.Random

object GenerationService {

  def generateString(rules: List[Rule]): String =
    StringService.generate(rules)

  def generateInteger(rules: List[Rule]): Int =
    IntegerService.generate(rules)

  def generateDecimal(rules: List[Rule]): Double =
    DecimalService.generate(rules)

  def generateBoolean(rules: List[Rule]): Boolean =
    BooleanService.generate(rules)

}
