package holydrinker.chromosoma.generation

import holydrinker.chromosoma.model.{ BooleanRule, Rule }

import scala.util.Random

trait SingleTypeGenerationService[T] {
  def generate[R <: Rule](rules: List[R]): T
}

/**
  * Exposes utilities to generate integers within the generation engine.
  */
object IntegerService extends SingleTypeGenerationService[Int] {

  override def generate[R <: Rule](rules: List[R]): Int = {
    val seed = Random.nextFloat()
    GenerationUtils.generateDecimal(seed, rules).toInt
  }

}

/**
  * Exposes utilities to generate decimals within the generation engine.
  */
object DecimalService extends SingleTypeGenerationService[Double] {
  override def generate[R <: Rule](rules: List[R]): Double = {
    val seed = Random.nextFloat()
    GenerationUtils.generateDecimal(seed, rules)
  }
}

/**
  * Exposes utilities to generate booleans within the generation engine.
  */
object BooleanService extends SingleTypeGenerationService[Boolean] {
  override def generate[R <: Rule](rules: List[R]): Boolean = {
    assert(rules.size == 1)
    val rule = getStandardBooleanRuleIfNoRulesProvided(rules)
    val seed = Random.nextFloat()
    GenerationUtils.generateBoolean(seed, rule)
  }

  private def getStandardBooleanRuleIfNoRulesProvided[R <: Rule](rules: List[R]): BooleanRule =
    rules.headOption match {
      case Some(rule: BooleanRule) =>
        rule
      case None =>
        BooleanRule(`true` = 0.5, `false` = 0.5)
    }
}

/**
  * Exposes utilities to generate strings within the generation engine.
  */
object StringService extends SingleTypeGenerationService[String] {

  /**
    * Generate a random string
    * @param rules generation rules
    * @return the string
    */
  override def generate[R <: Rule](rules: List[R]): String = {
    val seed = Random.nextFloat()
    GenerationUtils.generateString(seed, rules)
  }

}
