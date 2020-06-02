package holydrinker.chromosoma.writers

import java.io.File
import java.nio.file.Paths
import com.sksamuel.avro4s.{ AvroInputStream, AvroSchema, RecordFormat }
import holydrinker.chromosoma.TempDirSupport
import holydrinker.chromosoma.model.Dataset
import munit.FunSuite

class DatasetWriterSuite extends FunSuite with TempDirSupport {

  test("avro writer") {
    withTempDir { tempDir =>
      // Create dataset
      case class Artist(name: String, surname: String, age: Int, instrument: String)
      val artistSchema = AvroSchema[Artist]
      val artistFormat = RecordFormat[Artist]

      val deserializedArtists = Seq(
        Artist("simon", "neil", 41, "guitar"),
        Artist("martin", "mendez", 42, "bass")
      )

      val serializedArtists = deserializedArtists.map(artist => artistFormat.to(artist))

      val artistDataset = Dataset(schema = artistSchema, rows = serializedArtists)

      // Write dataset
      val resultPath = Paths.get(tempDir.getAbsolutePath, "result.avro").toAbsolutePath.toString
      println(resultPath)
      new AvroWriter().save(artistDataset, resultPath)

      // Check dataset
      val is = AvroInputStream
        .data[Artist]
        .from(new File(resultPath))
        .build(artistSchema)

      val actualDeserializedArtists = is.iterator.toSet
      is.close()

      val expectedDeserializedArtists = deserializedArtists.toSet

      assert(actualDeserializedArtists == expectedDeserializedArtists)
    }
  }

}
