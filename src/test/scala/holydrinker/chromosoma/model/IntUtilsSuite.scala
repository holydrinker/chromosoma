package holydrinker.chromosoma.model

import munit.FunSuite

class IntUtilsSuite extends FunSuite {

  test("low corner case") {
    val rules = List(
      RangeRule(Range(0, 50), DistributionValue(.8)),
      RangeRule(Range(51, 100), DistributionValue(.2))
    )
    val actual = IntUtils.generateInt(0.0, rules)
    assert(0 <= actual && actual <= 50)
  }

  test("under low-mid corner case") {
    val rules = List(
      RangeRule(Range(0, 50), DistributionValue(.8)),
      RangeRule(Range(51, 100), DistributionValue(.2))
    )
    val actual = IntUtils.generateInt(.79, rules)
    assert(0 <= actual && actual <= 50)
  }

  test("equals low-mid corner case") {
    val rules = List(
      RangeRule(Range(0, 50), DistributionValue(.8)),
      RangeRule(Range(51, 100), DistributionValue(.2))
    )
    val actual = IntUtils.generateInt(.8, rules)
    assert(0 <= actual && actual <= 50)
  }

  test("over low-mid corner case") {
    val rules = List(
      RangeRule(Range(0, 50), DistributionValue(.8)),
      RangeRule(Range(51, 100), DistributionValue(.2))
    )
    val actual = IntUtils.generateInt(.21, rules)
    assert(0 <= actual && actual <= 50)
  }

  test("high corner case") {
    val rules = List(
      RangeRule(Range(0, 50), DistributionValue(.8)),
      RangeRule(Range(51, 100), DistributionValue(.2))
    )
    val actual = IntUtils.generateInt(1, rules)
    assert(51 <= actual && actual <= 100)
  }

  test("mixing range and set") {
    val rules = List(
      RangeRule(Range(0, 10), DistributionValue(.8)),
      IntSetRule(Set(100, 1000), DistributionValue(.2))
    )
    val itemFromRange = IntUtils.generateInt(.3, rules)
    val itemFromSet   = IntUtils.generateInt(0.9, rules)

    assert(itemFromRange >= 0 && itemFromRange <= 10)
    assert(itemFromSet == 100 || itemFromSet == 1000)
  }

}
