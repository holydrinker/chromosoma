package holydrinker.chromosoma.generation

import holydrinker.chromosoma.model.{ BooleanRule, RangeRule, StringSetRule }
import munit.FunSuite

class SingleTypeGenerationServiceSuite extends FunSuite {

  test("Basic integer generation") {

    val rules = List(
      RangeRule(10, 100, 1.0)
    )

    val generatedInt = IntegerService.generate(rules)
    assert(generatedInt >= 10 && generatedInt <= 100)
  }

  test("Basic decimal generation") {

    val rules = List(
      RangeRule(10, 100, 1.0)
    )

    val generatedInt = DecimalService.generate(rules)
    assert(generatedInt >= 10 && generatedInt <= 100)
  }

  test("Basic boolean generation") {
    val trueValue = BooleanService.generate(
      List(
        BooleanRule(`false` = 0, `true` = 1)
      )
    )
    assert(trueValue)

    val falseValue = BooleanService.generate(
      List(
        BooleanRule(`false` = 1, `true` = 0)
      )
    )
    assert(!falseValue)
  }

  test("Basic string generation") {
    val set1 = Set("bass", "guitar", "drums")
    val set2 = Set("voice", "piano")

    val rules = List(
      StringSetRule(set1, 0.5),
      StringSetRule(set2, 0.5)
    )

    val result = StringService.generate(rules)
    set1.union(set2).contains(result)
  }

}
