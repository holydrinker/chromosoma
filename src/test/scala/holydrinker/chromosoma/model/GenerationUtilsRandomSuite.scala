package holydrinker.chromosoma.model

import munit.FunSuite

trait GenerationUtilsRandomSuite extends FunSuite {

  test("low corner case") {
    val rules = List(
      IntRangeRule(Range(0, 50), DistributionValue(.8)),
      IntRangeRule(Range(51, 100), DistributionValue(.2))
    )
    val actual = GeneratorUtils.generateZeroOneRandomFlag(0.0, rules)
    assert(0 <= actual && actual <= 50)
  }

  test("under low-mid corner case") {
    val rules = List(
      IntRangeRule(Range(0, 50), DistributionValue(.8)),
      IntRangeRule(Range(51, 100), DistributionValue(.2))
    )
    val actual = GeneratorUtils.generateZeroOneRandomFlag(.79, rules)
    assert(0 <= actual && actual <= 50)
  }

  test("equals low-mid corner case") {
    val rules = List(
      IntRangeRule(Range(0, 50), DistributionValue(.8)),
      IntRangeRule(Range(51, 100), DistributionValue(.2))
    )
    val actual = GeneratorUtils.generateZeroOneRandomFlag(.8, rules)
    assert(0 <= actual && actual <= 50)
  }

  test("over low-mid corner case") {
    val rules = List(
      IntRangeRule(Range(0, 50), DistributionValue(.8)),
      IntRangeRule(Range(51, 100), DistributionValue(.2))
    )
    val actual = GeneratorUtils.generateZeroOneRandomFlag(.21, rules)
    assert(0 <= actual && actual <= 50)
  }

  test("high corner case") {
    val rules = List(
      IntRangeRule(Range(0, 50), DistributionValue(.8)),
      IntRangeRule(Range(51, 100), DistributionValue(.2))
    )
    val actual = GeneratorUtils.generateZeroOneRandomFlag(1, rules)
    assert(51 <= actual && actual <= 100)
  }

}
