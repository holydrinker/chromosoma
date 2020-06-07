package holydrinker.chromosoma.generation

import holydrinker.chromosoma.model.Rule

import scala.util.Random

class GenerationService(rules: List[Rule]) {

  def generateString(size: Int): String =
    Random.alphanumeric.take(10).mkString

  def generateInteger: Int =
    IntGenerationService.generate(rules)

  def generateNumeric: Double =
    Random.nextDouble()

  def generateBoolean: Boolean =
    Random.nextBoolean()

}
