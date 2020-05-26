package holydrinker.chromosoma.core

sealed trait DataType

case object String extends DataType

case object Decimal extends DataType

case object Int extends DataType

case object Boolean extends DataType
