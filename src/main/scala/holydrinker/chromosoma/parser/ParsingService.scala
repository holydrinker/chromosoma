package holydrinker.chromosoma.parser

import java.io.{ FileInputStream, StringWriter }
import java.nio.charset.StandardCharsets

import io.circe._
import io.circe.parser._
import io.circe.generic.auto._
import cats.syntax.functor._
import holydrinker.chromosoma.model.{ BooleanRule, IntSetRule, RangeRule, Rule, StringSetRule }
import org.apache.commons.io.IOUtils

/**
  * Represents the staging area of the dataset information after its validation.
  * Here we save all the validated information needed to generate schema and data.
  *
  * @param instances number of the instance in the target dataset
  * @param output dataset file name
  * @param format dataset file format
  * @param fields list of [[ParsedChromoField]] that was parsed successfully
  */
case class Dna(instances: Int, output: String, format: String, fields: List[ParsedChromoField])

/**
  * Represents the target dataset schema.
  *
  * @param fields list of [[ParsedChromoField]
  */
case class ParsedChromoSchema(fields: List[ParsedChromoField])

/**
  * Staging data structure where are stored user defined fields with the correct syntax.
  *
  * @param name field's name
  * @param dataType field's type
  * @param rules rules that define the distribution to use in the generation of the current field
  */
case class ParsedChromoField(name: String, dataType: String, rules: List[Rule])

/**
  * Exposes utilities to parse the user-defined schema.
  */
object ParsingService {

  private implicit val rulesDecoder: Decoder[Rule] =
    List[Decoder[Rule]](
      Decoder[StringSetRule].widen,
      Decoder[IntSetRule].widen,
      Decoder[RangeRule].widen,
      Decoder[BooleanRule].widen
    ).reduceLeft(_ or _)

  /**
    * Parses the schema from the user-defined schema file path.
    *
    * @param path user-defined schema file path
    * @return maybe a [[Dna]]
    */
  def fromPath(path: String): Either[Error, Dna] = {
    val inputStream = new FileInputStream(path)
    val writer      = new StringWriter()
    IOUtils.copy(inputStream, writer, StandardCharsets.UTF_8)
    val rawJson = writer.toString

    decode[Dna](rawJson)
  }

}
