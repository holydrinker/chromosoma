package holydrinker.chromosoma.dsl

import holydrinker.chromosoma.model.{ChromoField, ChromoSchema, ChromoString, ChromoType, Dataset, Rule, StringSetRule}

object Chromosoma {

  def instances(n: Long): ChromosomaBuilder =
    new ChromosomaBuilder(instances = n)

}

class ChromosomaBuilder(
    instances: Long,
    fields: List[ChromoField] = List.empty[ChromoField]
) {

  def withField(name: String, dataType: ChromoType, rules: List[Rule]) = {
    val newField = ChromoField(
      name = name,
      dataType = dataType,
      rules = rules
    )
    new ChromosomaBuilder(instances = this.instances, fields = this.fields :+ newField)
  }

  def generate(): Dataset = {
    for {
      schema <- ChromoSchema.fromFields(this.fields)

    }

    schema  <- ChromoSchema.fromFields(dna.fields)
    dataset <- Dataset.fromSchema(schema, dna.instances)
  }

}

object Main {

  def main(args: Array[String]): Unit =
    Chromosoma
      .instances(10L)
      .withField(
        name = "name",
        dataType = ChromoString,
        rules = List(
          StringSetRule(values = Set("peppo", "paolo"), distribution = 1.0)
        )
      )

}
