package holydrinker.chromosoma.generation

import holydrinker.chromosoma.model.Rule

import scala.util.Random

object GenerationService {

  def generateString(size: Int): String =
    Random.alphanumeric.take(10).mkString

  def generateInteger(rules: List[Rule]): Int =
    IntGenerationService.generate(rules)

  def generateNumeric: Double =
    Random.nextDouble()

  def generateBoolean: Boolean =
    Random.nextBoolean()

}
