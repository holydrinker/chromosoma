package holydrinker.chromosoma.generation

import holydrinker.chromosoma.model.Rule

import scala.util.Random

object GenerationService {

  def generateString(size: Int): String =
    Random.alphanumeric.take(10).mkString

  def generateInteger(rules: List[Rule]): Int =
    IntegerService.generate(rules)

  def generateNumeric(rules: List[Rule]): Double =
    DecimalService.generate(rules)

  def generateBoolean(rules: List[Rule]): Boolean =
    BooleanService.generate(rules)

}
