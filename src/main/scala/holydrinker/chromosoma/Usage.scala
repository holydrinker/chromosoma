package holydrinker.chromosoma

import holydrinker.chromosoma.model.{ ChromoSchema, Dataset, DistributionValue, RangeRule }
import holydrinker.chromosoma.writers.DatasetWriter

object Usage {

  def main(args: Array[String]): Unit = {

    val inputSchema = getClass.getResourceAsStream("/usage-schema.txt")
    ChromoSchema
      .fromInputStream(inputSchema)
      .map(_.addRule(RangeRule(Range(0, 10), DistributionValue(1.0))))
      .map(schema => Dataset.fromSchema(schema, 10))
      .map(DatasetWriter("csv").save(_, "result.csv"))
  }

}
