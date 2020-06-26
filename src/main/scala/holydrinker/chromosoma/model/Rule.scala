package holydrinker.chromosoma.model

trait Rule {
  def distribution: Double
}

case class RangeRule(min: Int, max: Int, distribution: Double) extends Rule {
  def bounds = Range(min, max)
}

case class IntSetRule(values: Set[Int], distribution: Double) extends Rule

case class StringSetRule(values: Set[String], distribution: Double) extends Rule

case class BooleanRule(`true`: Double, `false`: Double) extends Rule {
  override def distribution: Double = `true` + `false`
}
