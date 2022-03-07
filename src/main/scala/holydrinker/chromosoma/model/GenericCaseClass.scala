package holydrinker.chromosoma.model

trait GenericCaseClass

case class StmInterruption(id: String, idCo: String) extends GenericCaseClass
//define rules for Stm Interruption
object StmInterruption extends StmInterruption("id", "idCo")

/**
  * USE CASES:
  * 1) /create => a partire dalle case class e regole definite, usare il DSL per creare il chromoSchema e salvarlo sul db
  * 2) /start => pescare dal db il chromoschema e usare il DatasetGenerator per creare evneti fake
  */
