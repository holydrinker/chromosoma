package holydrinker.chromosoma

import holydrinker.chromosoma.logging.ChromoLogger
import holydrinker.chromosoma.model.{ ChromoSchema, Dataset }
import holydrinker.chromosoma.parser.ParsingService

object Main extends ChromoLogger {

  def main(args: Array[String]): Unit = {
    val path = args(0)

//    for {
//      dna     <- ParsingService.fromPath(path)
//      schema  <- ChromoSchema.fromFields(dna.fields)
//      dataset <- Dataset.fromSchema(schema, dna.instances)
//    } DatasetWriter(dna.format).save(dataset, dna.output)

  }

}
