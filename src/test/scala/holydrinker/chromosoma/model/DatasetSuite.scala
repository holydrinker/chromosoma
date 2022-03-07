package holydrinker.chromosoma.model

import custom.{StmInterruption, StmRecordGenerator}
import holydrinker.chromosoma.generation.DatasetGenerator
import munit.FunSuite

class DatasetSuite extends FunSuite {

  test("simple schema with one range rule") {
    val stmFieldsfields = List(
      ChromoField(
        "id",
        ChromoString,
        List(
          StringSetRule(Set("id001", "id002"), 1.0)
        )
      ),
      ChromoField(
        "idCo",
        ChromoString,
        List(
          StringSetRule(Set("idCo001", "idCo002"), 1.0)
        )
      )
    )

    val schema = ChromoSchema(stmFieldsfields)
    val n      = 10

    val result: Dataset = new DatasetGenerator[StmInterruption](new StmRecordGenerator).fromSchema(schema, n).toOption.get

    result.rows.foreach(println)
  }

}
