package holydrinker.chromosoma.parser

import holydrinker.chromosoma.model.ChromoSchema

object ValidationService {

  def validate(schema: ParsedChromoSchema): Either[Exception, ChromoSchema] =
    TypeService
      .validateParsedSchema(schema)
      .flatMap(schema => SemanticsService.validateSchema(schema))

}
