package org.byrde.logger.impl

import com.google.inject.Inject

import org.byrde.guice.Akka
import org.byrde.logger.Logger

import akka.event.{ Logging, LoggingAdapter }

class ApplicationLogger @Inject() (akka: Akka) extends Logger {
  override protected def logger: LoggingAdapter =
    Logging(akka.system, getClass)
}
