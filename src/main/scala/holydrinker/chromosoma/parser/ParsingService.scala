package holydrinker.chromosoma.parser

import java.io.{ FileInputStream, StringWriter }
import java.nio.charset.StandardCharsets

import io.circe._
import io.circe.parser._
import io.circe.generic.auto._
import cats.syntax.functor._
import holydrinker.chromosoma.model.{ BooleanRule, IntSetRule, RangeRule, Rule, StringSetRule }
import org.apache.commons.io.IOUtils

case class Dna(instances: Int, output: String, format: String, fields: List[ParsedChromoField])

case class ParsedChromoSchema(fields: List[ParsedChromoField])

case class ParsedChromoField(name: String, dataType: String, rules: List[Rule])

object ParsingService {

  private implicit val rulesDecoder: Decoder[Rule] =
    List[Decoder[Rule]](
      Decoder[StringSetRule].widen,
      Decoder[IntSetRule].widen,
      Decoder[RangeRule].widen,
      Decoder[BooleanRule].widen
    ).reduceLeft(_ or _)

  def fromPath(path: String): Either[Error, Dna] = {
    val inputStream = new FileInputStream(path)
    val writer      = new StringWriter()
    IOUtils.copy(inputStream, writer, StandardCharsets.UTF_8)
    val rawJson = writer.toString

    decode[Dna](rawJson)
  }

}
