package holydrinker.chromosoma.generation

import holydrinker.chromosoma.model.{ BooleanRule, DistributionValue, RangeRule }
import munit.FunSuite

class SingleTypeGenerationServiceSuite extends FunSuite {

  test("basic int generation") {

    val rules = List(
      RangeRule(Range(10, 100), DistributionValue(1.0))
    )

    val generatedInt = IntegerService.generate(rules)
    assert(generatedInt >= 10 && generatedInt <= 100)
  }

  test("basic decimal generation") {

    val rules = List(
      RangeRule(Range(10, 100), DistributionValue(1.0))
    )

    val generatedInt = DecimalService.generate(rules)
    assert(generatedInt >= 10 && generatedInt <= 100)
  }

  test("basic boolean generation") {
    val trueValue = BooleanService.generate(
      List(
        BooleanRule(falseDistribution = DistributionValue(0), trueDistribution = DistributionValue(1))
      )
    )
    assert(trueValue)

    val falseValue = BooleanService.generate(
      List(
        BooleanRule(falseDistribution = DistributionValue(1), trueDistribution = DistributionValue(0))
      )
    )
    assert(!falseValue)
  }

}
