package org.byrde.models

import org.byrde.models.Todo.TodoId

import play.api.libs.json._
import play.api.libs.json.Reads._
import play.api.libs.functional.syntax._

case class Todo(id: TodoId, thing: String, completed: Boolean, priority: Int) {
  lazy val toJson: JsObject =
    Json.obj(
      "id" -> id,
      "thing" -> thing,
      "completed" -> completed,
      "priority" -> priority
    )

  def apply(newId: TodoId): Todo =
    Todo(newId, thing, completed, priority)
}

object Todo {
  private val MaxPriority: Int =
    10

  type TodoId = Long

  val readsNoId: Reads[Todo] =
    ((JsPath \ "thing").read[String] and
      (JsPath \ "completed").read[Boolean] and
      (JsPath \ "priority").read[Int].filter(_ <= MaxPriority))(Todo.create _)

  def readsWithId(todoId: TodoId): Reads[Todo] =
    (Reads.pure(todoId) and
      (JsPath \ "thing").read[String] and
      (JsPath \ "completed").read[Boolean] and
      (JsPath \ "priority").read[Int].filter(_ <= MaxPriority))(Todo.apply _)

  def create(thing: String, completed: Boolean, priority: Int): Todo =
    Todo(0, thing, completed, priority)

  def calculatePriorityAndMissingPriorityOccurrences(todos: Seq[Todo]): JsObject = {
    val priorityOccurrences =
      todos
        .groupBy(_.priority)
        .mapValues(_.size)

    val missingPriorityOccurrences =
      (0 to MaxPriority)
        .flatMap { i =>
          priorityOccurrences
            .get(i)
            .map(_ => None)
            .getOrElse(Some(i))
        }

    Json.obj(
      "missing-priorities" -> missingPriorityOccurrences,
      "priority-occurrences" ->
        priorityOccurrences.map {
          case (priority, occurences) =>
            Json.obj(
              "priority" -> priority,
              "occurrences" -> occurences
            )
        }
    )
  }
}
