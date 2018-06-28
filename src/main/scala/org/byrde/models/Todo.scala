package org.byrde.models

import org.byrde.models.Todo.TodoId

import play.api.libs.json._
import play.api.libs.json.Reads._
import play.api.libs.functional.syntax._

case class Todo(id: TodoId, thing: String, completed: Boolean) {
  lazy val toJson: JsObject =
    Json.obj(
      "id" -> id,
      "thing" -> thing,
      "completed" -> completed
    )

  def apply(newId: TodoId): Todo =
    Todo(newId, thing, completed)
}

object Todo {
  type TodoId = Long

  val readsNoId: Reads[Todo] =
    ((JsPath \ "thing").read[String] and
      (JsPath \ "completed").read[Boolean])(Todo.create _)

  def readsWithId(todoId: TodoId): Reads[Todo] =
    (Reads.pure(todoId) and
      (JsPath \ "thing").read[String] and
      (JsPath \ "completed").read[Boolean])(Todo.apply _)

  def create(thing: String, completed: Boolean): Todo =
    Todo(0, thing, completed)
}
