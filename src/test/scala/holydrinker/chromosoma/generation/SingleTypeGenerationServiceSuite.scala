package holydrinker.chromosoma.generation

import holydrinker.chromosoma.model.{ DistributionValue, RangeRule }
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

}
