package holydrinker.chromosoma.model

trait Rule

case class RangeRule(range: Range, distributionValue: DistributionValue) extends Rule with Distribution {
  override def distribution: Double = distributionValue.value
}

case class IntSetRule(set: Set[Int], distributionValue: DistributionValue) extends Rule with Distribution {
  override def distribution: Double = distributionValue.value
}

trait Distribution {
  def distribution: Double
}

case class DistributionValue(value: Double)
