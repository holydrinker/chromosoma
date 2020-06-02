package holydrinker.chromosoma.writers

import munit.FunSuite

class DatasetWriterSuite extends FunSuite {

  test("all format registered") {
    assert(DatasetWriter("avro").isInstanceOf[AvroWriter])
    assert(DatasetWriter("csv").isInstanceOf[CsvWriter])
  }

}
