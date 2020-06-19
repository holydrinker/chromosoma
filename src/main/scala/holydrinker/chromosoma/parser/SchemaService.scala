package holydrinker.chromosoma.parser

import java.io.{ FileInputStream, StringWriter }
import java.nio.charset.StandardCharsets

import io.circe.Decoder.Result
import io.circe._
import io.circe.parser._
import cats.syntax.functor._
import io.circe.generic.auto._
import org.apache.commons.io.IOUtils

case class ParsedChromoSchema(fields: List[ParsedChromoField])

case class ParsedChromoField(name: String, dataType: String, rules: List[MyRule])

sealed trait MyRule
case class MyStringSetRule(values: Set[String], distribution: Double) extends MyRule
case class MyIntSetRule(values: Set[Int], distribution: Double)       extends MyRule
case class MyRangeRule(min: Int, max: Int, distribution: Double)      extends MyRule
case class MyBooleanRule(`false`: Double, `true`: Double)             extends MyRule

object SchemaService {

  private implicit val rulesDecoder: Decoder[MyRule] =
    List[Decoder[MyRule]](
      Decoder[MyStringSetRule].widen,
      Decoder[MyIntSetRule].widen,
      Decoder[MyRangeRule].widen,
      Decoder[MyBooleanRule].widen
    ).reduceLeft(_ or _)

  def parseFromPath(path: String) = {
    val inputStream = new FileInputStream(path)
    val writer      = new StringWriter()
    IOUtils.copy(inputStream, writer, StandardCharsets.UTF_8)
    val rawJson = writer.toString

    decode[ParsedChromoSchema](rawJson)
  }

  def main(args: Array[String]): Unit = {
    val rawJsonPath = "src/main/resources/usage-schema.txt"
    val json        = parseFromPath(rawJsonPath)

    println(json)
  }

}
