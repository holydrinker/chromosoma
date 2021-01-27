package holydrinker.chromosoma.dsl

import holydrinker.chromosoma.error.{ ChromoError, ChromoMessages }
import holydrinker.chromosoma.model.{ ChromoString, Dataset, StringSetRule }
import munit.FunSuite

import scala.util.Try

class ChromosomaSuite extends FunSuite {

  test("empty fields") {
    val actual =
      Chromosoma
        .instances(1L)
        .generate()

    assert(actual == Left(ChromoError(ChromoMessages.cannotGenerate)))
  }

  test("empty fields unsafe") {
    Try(Chromosoma.instances(1L).generateUnsafe()).isFailure
  }

  test("single field") {
    val instances = 10L

    val generatedData =
      Chromosoma
        .instances(instances)
        .withField(
          name = "name",
          dataType = ChromoString,
          rules = List(
            StringSetRule(values = Set("peppo", "paolo"), distribution = 1.0)
          )
        )
        .generate()

    val actual = generatedData.flatMap(dataset => Right(dataset.rows.size == instances))
    assert(actual.isRight)
    assert(actual.getOrElse(false))
  }

  test("single field unsafe") {
    val instances = 10L

    val dataset =
      Chromosoma
        .instances(instances)
        .withField(
          name = "name",
          dataType = ChromoString,
          rules = List(
            StringSetRule(values = Set("name1", "name2"), distribution = 1.0)
          )
        )
        .generateUnsafe()

    assert(dataset.rows.size == instances)
  }

}
