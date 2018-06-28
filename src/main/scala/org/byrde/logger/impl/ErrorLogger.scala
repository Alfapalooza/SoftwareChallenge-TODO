package org.byrde.logger.impl

import com.google.inject.Inject

import org.byrde.guice.Akka
import org.byrde.logger.{ Logger, LoggingInformation }

import akka.event.{ Logging, LoggingAdapter }

class ErrorLogger @Inject() (akka: Akka) extends Logger {
  override protected val logger: LoggingAdapter =
    Logging(akka.system, getClass)

  def error[T](throwable: Throwable, elem: T)(implicit loggingInformation: LoggingInformation[(Throwable, T)]): Unit =
    logger.error(loggingInformation.log(throwable.getMessage, throwable -> elem).toString)
}
