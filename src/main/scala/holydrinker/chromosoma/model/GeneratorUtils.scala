package holydrinker.chromosoma.model

import scala.util.Random

case class DistributionSlot(rule: Rule with Distribution, slotUpperBound: Double)

object GeneratorUtils {

  def generateZeroOneRandomFlag[R <: Rule with Distribution](flag: Double, rules: List[R]): Int = {
    val slots        = GeneratorUtils.intRangeRulesToSlots(rules)
    val selectedRule = slots.dropWhile(flag > _.slotUpperBound).head.rule

    selectedRule match {
      case IntRangeRule(Range(start, end), _) =>
        start + Random.nextInt(end - start + 1)
      case _ =>
        -1
    }
  }

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
