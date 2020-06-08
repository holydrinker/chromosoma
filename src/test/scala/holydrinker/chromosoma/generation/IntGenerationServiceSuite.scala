package holydrinker.chromosoma.generation

import holydrinker.chromosoma.model.{ DistributionValue, RangeRule }
import munit.FunSuite

class IntGenerationServiceSuite extends FunSuite {

  test("basic int generation") {

    val rules = List(
      RangeRule(Range(10, 100), DistributionValue(1.0))
    )

    val generatedInt = IntGenerationService.generate(rules)
    println(generatedInt)
    assert(generatedInt >= 10 && generatedInt <= 100)
  }

}
