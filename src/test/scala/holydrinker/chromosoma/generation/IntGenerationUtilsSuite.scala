package holydrinker.chromosoma.generation

import holydrinker.chromosoma.model.{ DistributionValue, IntSetRule, RangeRule }
import munit.FunSuite

class IntGenerationUtilsSuite extends FunSuite {

  test("low corner case") {
    val rules = List(
      RangeRule(Range(0, 50), DistributionValue(.8)),
      RangeRule(Range(51, 100), DistributionValue(.2))
    )
    val actual = IntGenerationUtils.generateInt(0.0, rules)
    assert(0 <= actual && actual <= 50)
  }

  test("under low-mid corner case") {
    val rules = List(
      RangeRule(Range(0, 50), DistributionValue(.8)),
      RangeRule(Range(51, 100), DistributionValue(.2))
    )
    val actual = IntGenerationUtils.generateInt(.79, rules)
    assert(0 <= actual && actual <= 50)
  }

  test("equals low-mid corner case") {
    val rules = List(
      RangeRule(Range(0, 50), DistributionValue(.8)),
      RangeRule(Range(51, 100), DistributionValue(.2))
    )
    val actual = IntGenerationUtils.generateInt(.8, rules)
    assert(0 <= actual && actual <= 50)
  }

  test("over low-mid corner case") {
    val rules = List(
      RangeRule(Range(0, 50), DistributionValue(.8)),
      RangeRule(Range(51, 100), DistributionValue(.2))
    )
    val actual = IntGenerationUtils.generateInt(.21, rules)
    assert(0 <= actual && actual <= 50)
  }

  test("high corner case") {
    val rules = List(
      RangeRule(Range(0, 50), DistributionValue(.8)),
      RangeRule(Range(51, 100), DistributionValue(.2))
    )
    val actual = IntGenerationUtils.generateInt(1, rules)
    assert(51 <= actual && actual <= 100)
  }

  test("mixing range and set") {
    val rules = List(
      RangeRule(Range(0, 10), DistributionValue(.8)),
      IntSetRule(Set(100, 1000), DistributionValue(.2))
    )
    val itemFromRange = IntGenerationUtils.generateInt(.3, rules)
    val itemFromSet   = IntGenerationUtils.generateInt(0.9, rules)

    assert(itemFromRange >= 0 && itemFromRange <= 10)
    assert(itemFromSet == 100 || itemFromSet == 1000)
  }

  test("basic slot generation") {
    val rules = List(
      RangeRule(Range(0, 50), DistributionValue(.8)),
      RangeRule(Range(51, 100), DistributionValue(.2))
    )

    val actual = IntGenerationUtils.intRangeRulesToSlots(rules)

    val expected = List(
      DistributionSlot(RangeRule(Range(0, 50), DistributionValue(.8)), .8),
      DistributionSlot(RangeRule(Range(51, 100), DistributionValue(.2)), 1)
    )

    assert(actual == expected)
  }

  test("lots of equal slots") {
    val rules = List(
      RangeRule(Range(1, 10), DistributionValue(.1)),
      RangeRule(Range(11, 20), DistributionValue(.1)),
      RangeRule(Range(21, 30), DistributionValue(.1)),
      RangeRule(Range(31, 40), DistributionValue(.1)),
      RangeRule(Range(41, 50), DistributionValue(.1)),
      RangeRule(Range(51, 60), DistributionValue(.1)),
      RangeRule(Range(61, 70), DistributionValue(.1)),
      RangeRule(Range(71, 80), DistributionValue(.1)),
      RangeRule(Range(81, 90), DistributionValue(.1)),
      RangeRule(Range(91, 100), DistributionValue(.1))
    )

    val actual = IntGenerationUtils.intRangeRulesToSlots(rules)

    val expected = List(
      DistributionSlot(RangeRule(Range(1, 10), DistributionValue(.1)), 0.1),
      DistributionSlot(RangeRule(Range(11, 20), DistributionValue(.1)), 0.2),
      DistributionSlot(RangeRule(Range(21, 30), DistributionValue(.1)), 0.3),
      DistributionSlot(RangeRule(Range(31, 40), DistributionValue(.1)), 0.4),
      DistributionSlot(RangeRule(Range(41, 50), DistributionValue(.1)), 0.5),
      DistributionSlot(RangeRule(Range(51, 60), DistributionValue(.1)), 0.6),
      DistributionSlot(RangeRule(Range(61, 70), DistributionValue(.1)), 0.7),
      DistributionSlot(RangeRule(Range(71, 80), DistributionValue(.1)), 0.8),
      DistributionSlot(RangeRule(Range(81, 90), DistributionValue(.1)), 0.9),
      DistributionSlot(RangeRule(Range(91, 100), DistributionValue(.1)), 1)
    )

    assert(actual == expected)
  }

}
