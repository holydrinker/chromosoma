package holydrinker.generator.core


sealed trait DataType

case object DataTypeString extends DataType

case object DataTypeDecimal extends DataType

case object DataTypeInt extends DataType

case object DataTypeBoolean extends DataType
