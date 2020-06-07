package holydrinker.chromosoma.generation

import holydrinker.chromosoma.model.Rule

import scala.util.Random

trait SingleTypeGenerationService[T] {
  def generate[R <: Rule](rules: List[R]): T
}

object IntGenerationService extends SingleTypeGenerationService[Int] {

  override def generate[R <: Rule](rules: List[R]): Int = {
    val seed = Random.nextFloat()
    IntGenerationUtils.generateInt(seed, rules)
  }

}

object Runner {

  def main(args: Array[String]): Unit = {}

}
