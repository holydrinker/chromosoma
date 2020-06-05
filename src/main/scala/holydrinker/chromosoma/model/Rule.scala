package holydrinker.chromosoma.model

trait Rule

trait Distribution {
  def distribution: Double
}

case class IntRangeRule(range: Range, distributionValue: DistributionValue) extends Rule with Distribution {
  override def distribution: Double = distributionValue.value
}

case class Range(start: Int, end: Int)

case class DistributionValue(value: Double)
