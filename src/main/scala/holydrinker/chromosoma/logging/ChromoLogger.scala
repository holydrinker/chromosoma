package holydrinker.chromosoma.logging

import org.slf4j.LoggerFactory

trait ChromoLogger {

  private lazy val logger = LoggerFactory.getLogger(getClass.getName)

  def logDebug(msg: String): Unit =
    logger.debug(msg)

  def logInfo(msg: String): Unit =
    logger.info(msg)

  def logWarn(msg: String): Unit =
    logger.warn(msg)

  def logError(msg: String): Unit =
    logger.error(msg)

}
