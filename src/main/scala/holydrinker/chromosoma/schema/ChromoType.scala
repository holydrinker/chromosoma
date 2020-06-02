package holydrinker.chromosoma.schema

sealed trait DataType

case object ChromoString extends DataType

case object ChromoDecimal extends DataType

case object ChromoInt extends DataType

case object ChromoBoolean extends DataType
