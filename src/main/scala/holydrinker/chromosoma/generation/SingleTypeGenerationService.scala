package holydrinker.chromosoma.generation

import holydrinker.chromosoma.model.{ BooleanRule, DistributionValue, Rule }

import scala.util.Random

trait SingleTypeGenerationService[T] {
  def generate[R <: Rule](rules: List[R]): T
}

object IntegerService extends SingleTypeGenerationService[Int] {

  override def generate[R <: Rule](rules: List[R]): Int = {
    val seed = Random.nextFloat()
    Utils.generateDecimal(seed, rules).toInt
  }

}

object DecimalService extends SingleTypeGenerationService[Double] {
  override def generate[R <: Rule](rules: List[R]): Double = {
    val seed = Random.nextFloat()
    Utils.generateDecimal(seed, rules)
  }
}

object BooleanService extends SingleTypeGenerationService[Boolean] {
  override def generate[R <: Rule](rules: List[R]): Boolean = {
    assert(rules.size == 1)
    val rule = getStandardBooleanRuleIfNoRulesProvided(rules)
    val seed = Random.nextFloat()
    Utils.generateBoolean(seed, rule)
  }

  private def getStandardBooleanRuleIfNoRulesProvided[R <: Rule](rules: List[R]): BooleanRule =
    rules.headOption match {
      case Some(rule: BooleanRule) =>
        rule
      case None =>
        BooleanRule(trueDistribution = DistributionValue(0.5), falseDistribution = DistributionValue(0.5))
    }
}

object StringService extends SingleTypeGenerationService[String] {
  override def generate[R <: Rule](rules: List[R]): String = {
    val seed = Random.nextFloat()
    Utils.generateString(seed, rules)
  }

}
