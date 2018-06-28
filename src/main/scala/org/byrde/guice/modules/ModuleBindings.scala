package org.byrde.guice.modules

import net.codingwell.scalaguice.ScalaModule

import com.google.inject.AbstractModule

import org.byrde.configuration.Configuration
import org.byrde.guice.{ Akka, ModulesProvider }
import org.byrde.logger.impl.{ ApplicationLogger, ErrorLogger, RequestLogger }

/**
 * Using Guice for easy overriding of dependencies for Runtime vs. Tests
 */
class ModuleBindings extends AbstractModule with ScalaModule {
  override def configure(): Unit = {
    bind[Configuration].asEagerSingleton()
    bind[Akka].asEagerSingleton()
    bind[ApplicationLogger].asEagerSingleton()
    bind[RequestLogger].asEagerSingleton()
    bind[ErrorLogger].asEagerSingleton()
    bind[ModulesProvider]
  }
}
