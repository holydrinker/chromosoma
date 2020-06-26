package holydrinker.chromosoma.generation

import holydrinker.chromosoma.model.{ BooleanRule, IntSetRule, RangeRule, Rule, StringSetRule }

import scala.util.Random

object Utils {

  case class DistributionSlot(rule: Rule, slotUpperBound: Double)

  def generateBoolean(seed: Double, rule: BooleanRule): Boolean =
    if (seed <= rule.`true`)
      true
    else
      false

  def generateString[R <: Rule](seed: Double, rules: List[R]): String = {
    val selectedRule = selectRule(seed, rules)
    selectedRule match {
      case StringSetRule(set, _) =>
        set.toList(Random.nextInt(set.size))
    }
  }

  def generateDecimal[R <: Rule](seed: Double, rules: List[R]): Double = {
    val selectedRule = selectRule(seed, rules)
    selectedRule match {
      case RangeRule(min, max, _) =>
        pickRandomDoubleWithinRange(Range(min, max))
      case IntSetRule(set, _) =>
        pickRandomValueWithinSet(set)
    }
  }

  def selectRule[R <: Rule](flag: Double, rules: List[R]): Rule = {
    val slots = rangeRulesToSlots(rules)
    slots
      .dropWhile(flag > _.slotUpperBound)
      .head
      .rule
  }

  def rangeRulesToSlots(rules: List[Rule]): List[DistributionSlot] = {

    def loop(
        rules: List[Rule],
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

  def pickRandomValueWithinSet[T](set: Set[T]): T =
    set.toList(Random.nextInt(set.size))

  private def pickRandomDoubleWithinRange(range: Range): Double =
    range.start + Random.nextDouble() * (range.end - range.start)

}
