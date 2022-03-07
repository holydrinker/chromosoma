package holydrinker.chromosoma.generation

import holydrinker.chromosoma.model.{ChromoField, ChromoSchema, ChromoString, StmInterruption}

import scala.annotation.tailrec

trait RecordGenerator[GenericCaseClass] {
  def makeGenericRecord(chromoSchema: ChromoSchema): GenericCaseClass
}

class StmRecordGenerator extends RecordGenerator[StmInterruption] {

  def makeGenericRecord(chromoSchema: ChromoSchema): StmInterruption = {
    @tailrec
    def recursiveStep(stmInterruption: StmInterruption, chromoFields: List[ChromoField]): StmInterruption = {
      if (chromoFields.isEmpty) {
        stmInterruption
      } else {
        val newStmInterruption = chromoFields.head match {
          case ChromoField("id", ChromoString, rules) =>
            stmInterruption.copy(id = GenerationService.generateString(rules))

          case ChromoField("idCo", ChromoString, rules) =>
            stmInterruption.copy(idCo = GenerationService.generateString(rules))
        }
        recursiveStep(newStmInterruption, chromoFields.tail)
      }
    }

    recursiveStep(StmInterruption, chromoSchema.fields)
  }

}
