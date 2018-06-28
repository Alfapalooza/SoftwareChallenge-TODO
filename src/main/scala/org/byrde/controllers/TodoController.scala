package org.byrde.controllers

import org.byrde.controllers.directives.ApiSupport
import org.byrde.models.Todo
import org.byrde.models.responses.todo.{ TodoResponse, TodoSequenceResponse }
import org.byrde.persistence.todo.TodoStorage

import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.directives.MarshallingEntityWithRequestDirective

import scala.concurrent.ExecutionContext

class TodoController(todoStorage: TodoStorage)(ec1: ExecutionContext)(implicit ec2: ExecutionContext) extends ApiSupport with MarshallingEntityWithRequestDirective {
  lazy val routes: Route =
    path(IntNumber) { todoId =>
      fetch(todoId) ~ edit(todoId) ~ remove(todoId)
    } ~ fetchAll ~ create

  private def create =
    post {
      requestEntityUnmarshallerWithEntity(unmarshaller[Todo](Todo.readsNoId)) { request =>
        asyncJson(todoStorage.create(request.body)(ec1).map(TodoResponse.apply))
      }
    }

  private def edit(todoId: Long) =
    put {
      requestEntityUnmarshallerWithEntity(unmarshaller[Todo](Todo.readsWithId(todoId))) { request =>
        asyncJson(todoStorage.update(todoId, request.body)(ec1).map(TodoResponse.apply))
      }
    }

  private def remove(todoId: Long) =
    delete {
      asyncJson(todoStorage.remove(todoId)(ec1).map(TodoResponse.apply))
    }

  private def fetch(todoId: Long) =
    get {
      asyncJson(todoStorage.fetch(todoId)(ec1).map(TodoResponse.apply))
    }

  private def fetchAll =
    get {
      asyncJson(todoStorage.fetchAll(ec1).map(TodoSequenceResponse.apply))
    }
}
