package holydrinker.chromosoma.generation

import scala.util.Random

object Generation {

  def generateString(size: Int): String =
    Random.alphanumeric.take(10).mkString

  def generateInteger: Int =
    Random.nextInt()

  def generateNumeric: Double =
    Random.nextDouble()

  def generateBoolean: Boolean =
    Random.nextBoolean()

}
