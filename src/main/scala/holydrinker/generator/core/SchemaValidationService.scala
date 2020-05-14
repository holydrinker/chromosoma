package holydrinker.generator.core

import java.io.InputStream
import scala.io.Source

object SchemaValidationService {

  type FieldError  = String
  type SchemaError = String

  private case class ValidationAccumulators(errorAcc: Seq[FieldError] = Nil, fieldsAcc: Seq[RowFields] = Nil)

  private case class SemanticAccumulators(errors: Seq[FieldError] = Nil, fields: Seq[Field] = Nil)

  def extractRowFieldsFromStream(input: InputStream): Either[FieldError, Seq[RowFields]] = {
    val rowFields = Source
      .fromInputStream(input)
      .getLines()
      .toSeq
      .zipWithIndex
      .map {
        case (line, idx) =>
          tryToMakeRowField(line, idx)
      }

    val accResult = rowFields.foldLeft(ValidationAccumulators()) {
      (acc: ValidationAccumulators, either: Either[FieldError, RowFields]) =>
        either match {
          case Right(rowField) =>
            acc.copy(fieldsAcc = acc.fieldsAcc.appended(rowField))
          case Left(errorLine) =>
            acc.copy(errorAcc = acc.errorAcc.appended(errorLine))
        }
    }

    accResult match {
      case ValidationAccumulators(Nil, fields) =>
        Right(fields)
      case ValidationAccumulators(errors, Nil) =>
        val errorMsg = s"Invalid schema syntax: [${errors.mkString("\n")}]"
        Left(errorMsg)
    }
  }

  private def tryToMakeRowField(line: FieldError, linePos: Int): Either[String, RowFields] = {
    val splitLine = line.split(" ")
    if (splitLine.size == 2) {
      val fieldName = splitLine(0)
      val fieldType = splitLine(1)
      Right(RowFields(fieldName, fieldType))
    } else {
      Left(line)
    }
  }

  def validateFields(rowFields: Seq[RowFields]): Either[SchemaError, Seq[Field]] = {
    val accumulator = rowFields
      .map(_.validate())
      .foldLeft(SemanticAccumulators()) { (acc: SemanticAccumulators, either: Either[String, Field]) =>
        either match {
          case Right(field) => acc.copy(fields = acc.fields.appended(field))
          case Left(error)  => acc.copy(errors = acc.errors.appended(error))
        }
      }

    accumulator match {
      case SemanticAccumulators(Nil, fields) => Right(fields)
      case SemanticAccumulators(errors, _)   => Left(s"Invalid datatype in fields: [name,surname]")
    }
  }
}
