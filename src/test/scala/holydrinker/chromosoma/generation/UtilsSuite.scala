package holydrinker.chromosoma.generation

import holydrinker.chromosoma.generation.Utils.DistributionSlot
import holydrinker.chromosoma.model.{ BooleanRule, IntSetRule, RangeRule, StringSetRule }
import munit.FunSuite

class UtilsSuite extends FunSuite {

  test("low corner case") {
    val rules = List(
      RangeRule(0, 50, .8),
      RangeRule(51, 100, .2)
    )
    val actual = Utils.generateDecimal(0.0, rules)
    assert(0 <= actual && actual <= 50)
  }

  test("under low-mid corner case") {
    val rules = List(
      RangeRule(0, 50, .8),
      RangeRule(51, 100, .2)
    )
    val actual = Utils.generateDecimal(.79, rules)
    assert(0 <= actual && actual <= 50)
  }

  test("equals low-mid corner case") {
    val rules = List(
      RangeRule(0, 50, .8),
      RangeRule(51, 100, .2)
    )
    val actual = Utils.generateDecimal(.8, rules)
    assert(0 <= actual && actual <= 50)
  }

  test("over low-mid corner case") {
    val rules = List(
      RangeRule(0, 50, .8),
      RangeRule(51, 100, .2)
    )
    val actual = Utils.generateDecimal(.21, rules)
    assert(0 <= actual && actual <= 50)
  }

  test("high corner case") {
    val rules = List(
      RangeRule(0, 50, .8),
      RangeRule(51, 100, .2)
    )
    val actual = Utils.generateDecimal(1, rules)
    assert(51 <= actual && actual <= 100)
  }

  test("mixing range and set") {
    val rules = List(
      RangeRule(0, 10, .8),
      IntSetRule(Set(100, 1000), .2)
    )
    val itemFromRange = Utils.generateDecimal(.3, rules)
    val itemFromSet   = Utils.generateDecimal(0.9, rules)

    assert(itemFromRange >= 0 && itemFromRange <= 10)
    assert(itemFromSet == 100 || itemFromSet == 1000)
  }

  test("basic slot generation") {
    val rules = List(
      RangeRule(0, 50, .8),
      RangeRule(51, 100, .2)
    )

    val actual = Utils.rangeRulesToSlots(rules)

    val expected = List(
      DistributionSlot(RangeRule(0, 50, .8), .8),
      DistributionSlot(RangeRule(51, 100, .2), 1)
    )

    assert(actual == expected)
  }

  test("lots of equal slots") {
    val rules = List(
      RangeRule(1, 10, .1),
      RangeRule(11, 20, .1),
      RangeRule(21, 30, .1),
      RangeRule(31, 40, .1),
      RangeRule(41, 50, .1),
      RangeRule(51, 60, .1),
      RangeRule(61, 70, .1),
      RangeRule(71, 80, .1),
      RangeRule(81, 90, .1),
      RangeRule(91, 100, .1)
    )

    val actual = Utils.rangeRulesToSlots(rules)

    val expected = List(
      DistributionSlot(RangeRule(1, 10, .1), 0.1),
      DistributionSlot(RangeRule(11, 20, .1), 0.2),
      DistributionSlot(RangeRule(21, 30, .1), 0.3),
      DistributionSlot(RangeRule(31, 40, .1), 0.4),
      DistributionSlot(RangeRule(41, 50, .1), 0.5),
      DistributionSlot(RangeRule(51, 60, .1), 0.6),
      DistributionSlot(RangeRule(61, 70, .1), 0.7),
      DistributionSlot(RangeRule(71, 80, .1), 0.8),
      DistributionSlot(RangeRule(81, 90, .1), 0.9),
      DistributionSlot(RangeRule(91, 100, .1), 1)
    )

    assert(actual == expected)
  }

  test("generate boolean") {
    val rule = BooleanRule(`false` = 0.4, `true` = 0.6)

    val trueValue = Utils.generateBoolean(0.2, rule)
    assert(trueValue)

    val falseValue = Utils.generateBoolean(0.7, rule)
    assert(!falseValue)

  }

  test("Simple string generation") {
    val set1 = Set("bass", "guitar", "drums")
    val set2 = Set("voice", "piano")

    val rules = List(
      StringSetRule(set1, 0.5),
      StringSetRule(set2, 0.5)
    )
    val result1 = Utils.generateString(0.1, rules)
    assert(set1.contains(result1))

    val result2 = Utils.generateString(0.8, rules)
    assert(set2.contains(result2))
  }

}
