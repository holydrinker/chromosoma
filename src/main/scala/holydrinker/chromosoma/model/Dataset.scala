package holydrinker.chromosoma.model

import cats.data.Validated
import cats.data.Validated.Valid
import custom.{GenericCaseClass, RecordGenerator}
import holydrinker.chromosoma.error.ChromoError
import holydrinker.chromosoma.logging.ChromoLogger

/**
  * Represents a dataset with its row and columns information.
  *
  * @param rows rows information
  * @param schema columns information
  */
case class Dataset(rows: Seq[GenericCaseClass])
