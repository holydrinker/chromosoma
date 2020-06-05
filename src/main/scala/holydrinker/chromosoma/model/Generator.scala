package holydrinker.chromosoma.model

import scala.util.Random

trait Generator[T] {
  def generate[R <: Rule with Distribution](rules: List[R]): T
}

object IntGenerator extends Generator[Int] {

  override def generate[R <: Rule with Distribution](rules: List[R]): Int =
    GeneratorUtils.generateZeroOneRandomFlag(Random.nextFloat(), rules)

}

object Runner {

  def main(args: Array[String]): Unit = {}

}
