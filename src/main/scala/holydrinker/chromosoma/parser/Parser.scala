package holydrinker.chromosoma.parser

import java.nio.charset.StandardCharsets

import spray.json.DefaultJsonProtocol.{ jsonFormat1, jsonFormat3 }

import java.io.InputStream

/*
object Parser {

  def fromInputStream(inputStream: InputStream): Unit = {
    val source                     = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8).parseJson
    implicit val chromoFieldFormat = jsonFormat3(NewChromoField)
    implicit val configFormat      = jsonFormat1(NewConfig)
    val config                     = source.convertTo[NewConfig]
    println(config)
  }

}
 */
