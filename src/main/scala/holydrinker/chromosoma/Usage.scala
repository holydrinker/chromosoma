package holydrinker.chromosoma

import holydrinker.chromosoma.model.Dataset
import holydrinker.chromosoma.schema.ChromoSchema
import holydrinker.chromosoma.writers.DatasetWriter

object Usage {

  def main(args: Array[String]): Unit = {

    val inputSchema = getClass.getResourceAsStream("/usage-schema.txt")
    ChromoSchema
      .fromInputStream(inputSchema)
      .map(schema => Dataset.fromSchema(schema, 10))
      .map(DatasetWriter("csv").save(_, "result.csv"))
  }

}
