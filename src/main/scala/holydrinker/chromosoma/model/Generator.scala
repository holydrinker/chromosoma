package holydrinker.chromosoma.model

import scala.util.Random

trait Generator[T] {
  def generate[R <: Rule with Distribution](rules: List[R]): T
}

object IntGenerator extends Generator[Int] {

  override def generate[R <: Rule with Distribution](rules: List[R]): Int = {
    val seed = Random.nextFloat()
    IntUtils.generateInt(seed, rules)
  }

}

object Runner {

  def main(args: Array[String]): Unit = {}

}
