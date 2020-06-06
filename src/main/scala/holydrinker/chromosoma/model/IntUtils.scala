package holydrinker.chromosoma.model

import scala.util.Random

case class DistributionSlot(rule: Rule with Distribution, slotUpperBound: Double)

object IntUtils {

  def generateInt[R <: Rule with Distribution](seed: Double, rules: List[R]): Int = {
    val selectedRule = selectRule(seed: Double, rules: List[R])
    selectedRule match {
      case RangeRule(range, _) =>
        pickRandomNumberWithinRange(range)
      case IntSetRule(set, _) =>
        pickRandomValueWithinSet(set)
    }
  }

  private def selectRule[R <: Rule with Distribution](flag: Double, rules: List[R]): Rule = {
    val slots = IntUtils.intRangeRulesToSlots(rules)
    slots
      .dropWhile(flag > _.slotUpperBound)
      .head
      .rule
  }

  private def pickRandomNumberWithinRange(range: Range): Int =
    range.start + Random.nextInt(range.end - range.start + 1)

  private def pickRandomValueWithinSet[T](set: Set[T]): T =
    set.toList(Random.nextInt(set.size))

  def intRangeRulesToSlots(rules: List[Rule with Distribution]): List[DistributionSlot] = {

    def loop(
        rules: List[Rule with Distribution],
        prevSlotUpperBound: Double,
        acc: List[DistributionSlot]
    ): List[DistributionSlot] = rules match {
      case Nil => acc
      case x :: xs =>
        val newSlotUpperBound = preciseDoubleSum(prevSlotUpperBound, x.distribution)
        loop(
          rules = xs,
          prevSlotUpperBound = newSlotUpperBound,
          acc = acc ::: List(DistributionSlot(x, newSlotUpperBound))
        )
    }

    loop(rules, 0.0, List.empty[DistributionSlot])
  }

  private def preciseDoubleSum(x: Double, y: Double): Double =
    (BigDecimal(x) + BigDecimal(y)).toDouble

}
