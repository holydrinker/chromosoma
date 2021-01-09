package holydrinker.chromosoma.generation

import holydrinker.chromosoma.model.{ BooleanRule, IntSetRule, RangeRule, Rule, StringSetRule }

import scala.util.Random

/**
  * Exposes utilities to be used in the [[GenerationService]]
  */
object GenerationUtils {

  case class DistributionSlot(rule: Rule, slotUpperBound: Double)

  /**
    * Generates a pseudo-random boolean
    * @param seed the "pseudo" in "pseudo-random"
    * @param rule generation rules
    * @return the boolean
    */
  def generateBoolean(seed: Double, rule: BooleanRule): Boolean =
    if (seed <= rule.`true`)
      true
    else
      false

  /**
    * Generates a pseudo-random string
    * @param seed the "pseudo" in "pseudo-random"
    * @param rules generation rules
    * @return the string
    */
  def generateString[R <: Rule](seed: Double, rules: List[R]): String = {
    val selectedRule = selectRule(seed, rules)
    selectedRule match {
      case StringSetRule(set, _) =>
        set.toList(Random.nextInt(set.size))
    }
  }

  /**
    * Generates a pseudo-random decimal
    * @param seed the "pseudo" in "pseudo-random"
    * @param rules generation rules
    * @return the decimal
    */
  def generateDecimal[R <: Rule](seed: Double, rules: List[R]): Double = {
    val selectedRule = selectRule(seed, rules)
    selectedRule match {
      case RangeRule(min, max, _) =>
        pickRandomDoubleWithinRange(Range(min, max))
      case IntSetRule(set, _) =>
        pickRandomValueWithinSet(set)
    }
  }

  /**
    * Select a pseudo-random rule
    * @param flag the "pseudo" in "pseudo-random"
    * @param rules the rules
    * @return the selected rule
    */
  def selectRule[R <: Rule](flag: Double, rules: List[R]): Rule = {
    val slots = rangeRulesToSlots(rules)
    slots
      .dropWhile(flag > _.slotUpperBound)
      .head
      .rule
  }

  /**
    * Transforms a list of rules in a list of distribution slots.
    * Let's say we have two rules:
    * - RULE1 => 80% probability to be selected
    * - RULE2 => 20% probability to be selected
    *
    * This algorithm creates the following structure:
    *
    * |********************************|********|
    *
    * The first slot represents RULE1 and the second RULE2.
    * This structure is very useful to pick a random `*` and still be sure that the probability
    * of your choice is coherent the probability of the input rules.
    * The total area of the slots is scaled between 0 and 1.
    *
    * For each slot represented graphically, we keep the information about
    * the source rule and the slot upper bound.
    *
    * @param rules rules to be transformed in the distribution slots
    * @return the distribution slots
    */
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

  private def pickRandomValueWithinSet[T](set: Set[T]): T =
    set.toList(Random.nextInt(set.size))

  private def pickRandomDoubleWithinRange(range: Range): Double =
    range.start + Random.nextDouble() * (range.end - range.start)

}
