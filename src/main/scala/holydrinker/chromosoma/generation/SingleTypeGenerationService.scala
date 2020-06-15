package holydrinker.chromosoma.generation

import holydrinker.chromosoma.model.Rule

import scala.util.Random

trait SingleTypeGenerationService[T] {
  def generate[R <: Rule](rules: List[R]): T
}

object IntegerService extends SingleTypeGenerationService[Int] {

  override def generate[R <: Rule](rules: List[R]): Int = {
    val seed = Random.nextFloat()
    CommonUtils.generateDecimal(seed, rules).toInt
  }

}

object DecimalService extends SingleTypeGenerationService[Double] {
  override def generate[R <: Rule](rules: List[R]): Double = {
    val seed = Random.nextFloat()
    CommonUtils.generateDecimal(seed, rules)
  }
}
