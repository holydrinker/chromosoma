package holydrinker.chromosoma.model

import munit.FunSuite

trait GenerationUtilsSlotSuite extends FunSuite {

  test("basic case") {
    val rules = List(
      IntRangeRule(Range(0, 50), DistributionValue(.8)),
      IntRangeRule(Range(51, 100), DistributionValue(.2))
    )

    val actual = GeneratorUtils.intRangeRulesToSlots(rules)

    val expected = List(
      DistributionSlot(IntRangeRule(Range(0, 50), DistributionValue(.8)), .8),
      DistributionSlot(IntRangeRule(Range(51, 100), DistributionValue(.2)), 1)
    )

    assert(actual == expected)
  }

  test("lots of equal slots") {
    val rules = List(
      IntRangeRule(Range(1, 10), DistributionValue(.1)),
      IntRangeRule(Range(11, 20), DistributionValue(.1)),
      IntRangeRule(Range(21, 30), DistributionValue(.1)),
      IntRangeRule(Range(31, 40), DistributionValue(.1)),
      IntRangeRule(Range(41, 50), DistributionValue(.1)),
      IntRangeRule(Range(51, 60), DistributionValue(.1)),
      IntRangeRule(Range(61, 70), DistributionValue(.1)),
      IntRangeRule(Range(71, 80), DistributionValue(.1)),
      IntRangeRule(Range(81, 90), DistributionValue(.1)),
      IntRangeRule(Range(91, 100), DistributionValue(.1))
    )

    val actual = GeneratorUtils.intRangeRulesToSlots(rules)

    val expected = List(
      DistributionSlot(IntRangeRule(Range(1, 10), DistributionValue(.1)), 0.1),
      DistributionSlot(IntRangeRule(Range(11, 20), DistributionValue(.1)), 0.2),
      DistributionSlot(IntRangeRule(Range(21, 30), DistributionValue(.1)), 0.3),
      DistributionSlot(IntRangeRule(Range(31, 40), DistributionValue(.1)), 0.4),
      DistributionSlot(IntRangeRule(Range(41, 50), DistributionValue(.1)), 0.5),
      DistributionSlot(IntRangeRule(Range(51, 60), DistributionValue(.1)), 0.6),
      DistributionSlot(IntRangeRule(Range(61, 70), DistributionValue(.1)), 0.7),
      DistributionSlot(IntRangeRule(Range(71, 80), DistributionValue(.1)), 0.8),
      DistributionSlot(IntRangeRule(Range(81, 90), DistributionValue(.1)), 0.9),
      DistributionSlot(IntRangeRule(Range(91, 100), DistributionValue(.1)), 1)
    )

    assert(actual == expected)
  }
}
