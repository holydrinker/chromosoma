package holydrinker.chromosoma

import holydrinker.chromosoma.model.{ ChromoSchema, Dataset }
import holydrinker.chromosoma.writers.DatasetWriter

object Usage {

  def main(args: Array[String]): Unit = {
    val schema  = ChromoSchema.fromJson("src/main/resources/usage-schema.txt")
    val dataset = Dataset.fromSchema(schema, instances = 10)
    val writer  = DatasetWriter(format = "csv")
    writer.save(dataset, path = "result.csv")
  }

}
