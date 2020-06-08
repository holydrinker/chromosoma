package holydrinker.chromosoma.model

sealed trait Rule {
  def distribution: Double
}

case class RangeRule(bounds: Range, distributionValue: DistributionValue) extends Rule {
  override def distribution: Double = distributionValue.value
}

case class IntSetRule(values: Set[Int], distributionValue: DistributionValue) extends Rule {
  override def distribution: Double = distributionValue.value
}

case class DistributionValue(value: Double)
