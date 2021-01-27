package holydrinker.chromosoma.writers

import java.nio.file.Paths

import com.sksamuel.avro4s.{ AvroSchema, RecordFormat }
import holydrinker.chromosoma.TempDirSupport
import holydrinker.chromosoma.model.Dataset
import munit.FunSuite

class CsvWriterSuite extends FunSuite with TempDirSupport {

  test("csv writer") {
    withTempDir { tempDir =>
      case class Artist(name: String, surname: String, age: Int, instrument: String)
      val artistSchema = AvroSchema[Artist]
      val artistFormat = RecordFormat[Artist]
      val deserializedArtists = Seq(
        Artist("simon", "neil", 41, "guitar"),
        Artist("martin", "mendez", 42, "bass")
      )
      val serializedArtists = deserializedArtists.map(artist => artistFormat.to(artist))
      val artistDataset     = Dataset(schema = artistSchema, rows = serializedArtists)

      // Write dataset
      val resultPath = Paths.get(tempDir.getAbsolutePath, "result").toAbsolutePath.toString
      new CsvWriter(sep = ",").saveFile(artistDataset, resultPath)

      // Check dataset
      val actualArtistSet = scala.io.Source.fromFile(s"$resultPath").getLines().toSet

      val expectedArtistSet = Set(
        "simon,neil,41,guitar",
        "martin,mendez,42,bass"
      )

      assert(actualArtistSet == expectedArtistSet)
    }
  }

}
