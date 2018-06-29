package org.byrde

import org.byrde.controllers.TodoController
import org.byrde.controllers.directives.RequestResponseHandlingDirective
import org.byrde.guice.ModulesProvider
import org.byrde.models.responses.CommonJsonServiceResponseDictionary.E0200

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.directives.MarshallingEntityWithRequestDirective
import akka.util.Timeout

import scala.concurrent.ExecutionContext

trait Routes extends RequestResponseHandlingDirective with MarshallingEntityWithRequestDirective {
  implicit def timeout: Timeout

  def mainPool: ExecutionContext

  def storagePool: ExecutionContext

  def modulesProvider: ModulesProvider

  lazy val defaultRoutes: Route =
    complete(E0200("pong"))

  lazy val pathBindings =
    Map(
      "ping" -> defaultRoutes,
      "todo" -> new TodoController(modulesProvider.todoStorage)(storagePool, mainPool).routes
    )

  lazy val routes: Route =
    requestResponseHandler {
      pathBindings.map {
        case (k, v) => pathPrefix(k)(v)
      } reduce (_ ~ _)
    }
}
