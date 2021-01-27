package holydrinker.chromosoma.error

case class ChromoError(msg: String) extends Exception

object ChromoMessages {

  val cannotGenerate = "Cannot generate datasets without rules"

  def invalidBooleanRule(fieldName: String) =
    s"Invalid boolean rule for field $fieldName. be sure you have exactly one rule and that the overall distribution is equal to 1.0"

  def cannotValidate(fieldName: String) = s"Cannot validate field $fieldName"

  def stringFieldCannotContainsRangeRule(fieldName: String) =
    s"String field $fieldName cannot contains range rules"

  def integerFieldMustContainRangeRule(fieldName: String): String =
    s"Integer field $fieldName must contain only RangeRule and IntSetRule"

  def wrongDistribution(fieldName: String, distribution: BigDecimal): String =
    s"String field $fieldName has overallDistribution = $distribution. It shoud be equal to 1.0."
}
