package org.byrde.guice

import com.google.inject.Inject

import org.byrde.configuration.Configuration
import org.byrde.logger.impl.ApplicationLogger
import org.byrde.persistence.todo.TodoStorage
import org.byrde.persistence.todo.impl.TodoMemoryStorage

class ModulesProvider @Inject() (
    val configuration: Configuration,
    val akka: Akka,
    val applicationLogger: ApplicationLogger
) {
  val todoStorage: TodoStorage =
    TodoMemoryStorage
}
