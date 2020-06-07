package holydrinker.chromosoma.model

trait Rule {
  def distribution: Double
}

case class RangeRule(range: Range, distributionValue: DistributionValue) extends Rule {
  override def distribution: Double = distributionValue.value
}

case class IntSetRule(set: Set[Int], distributionValue: DistributionValue) extends Rule {
  override def distribution: Double = distributionValue.value
}

case class DistributionValue(value: Double)
