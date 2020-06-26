package holydrinker.chromosoma

import holydrinker.chromosoma.model.{ ChromoSchema, Dataset }
import holydrinker.chromosoma.writers.DatasetWriter

object Usage {

  def main(args: Array[String]): Unit = {
    val input     = "src/main/resources/usage-schema.txt"
    val instances = 10
    val format    = "csv"
    val output    = "result.csv"

    for {
      schema  <- ChromoSchema.fromJson(input)
      dataset <- Dataset.fromSchema(schema, instances)
    } DatasetWriter(format).save(dataset, output)

  }

}
