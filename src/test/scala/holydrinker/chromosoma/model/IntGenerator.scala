package holydrinker.chromosoma.model

import munit.FunSuite

class IntGeneratorSuite extends FunSuite {

  test("basic int generation") {

    val rules = List(
      RangeRule(Range(10, 100), DistributionValue(1.0))
    )

    val generatedInt = IntGenerator.generate(rules)
    println(generatedInt)
    assert(generatedInt >= 10 && generatedInt <= 100)
  }

}
