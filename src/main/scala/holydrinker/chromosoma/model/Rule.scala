package holydrinker.chromosoma.model

/**
  * Contains the information to generate the data for a generic feature.
  */
trait Rule {

  def distribution: Double
}

/**
  * When the rule is used as input for the generation engine,
  * a number between `min` and `max`` is generated.
  */
case class RangeRule(min: Int, max: Int, distribution: Double) extends Rule {

  /**
    * Returns the range.
    *
    * @return range
    */
  def bounds = Range(min, max)
}

/**
  * When the rule is used as input for the generation engine, a number is generated from the `values` set.
  */
case class IntSetRule(values: Set[Int], distribution: Double) extends Rule

/**
  * When the rule is used as input for the generation engine, a string is generated from the `values` set.
  */
case class StringSetRule(values: Set[String], distribution: Double) extends Rule

/**
  * When the rule is used as input for the generation engine, a boolean value is generated.
  */
case class BooleanRule(`true`: Double, `false`: Double) extends Rule {
  override def distribution: Double = `true` + `false`
}
