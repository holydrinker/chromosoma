package holydrinker.chromosoma

import holydrinker.chromosoma.model.{ ChromoSchema, Dataset }
import holydrinker.chromosoma.parser.ParsingService
import holydrinker.chromosoma.writers.DatasetWriter

object Usage {

  def main(args: Array[String]): Unit = {
    val input = "src/main/resources/dna.json"

    for {
      dna     <- ParsingService.fromPath(input)
      schema  <- ChromoSchema.fromFields(dna.fields)
      dataset <- Dataset.fromSchema(schema, dna.instances)
    } DatasetWriter(dna.format).save(dataset, dna.output)

  }

}
