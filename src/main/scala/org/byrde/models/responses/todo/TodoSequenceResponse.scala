package org.byrde.models.responses.todo

import org.byrde.models.Todo
import org.byrde.models.responses.JsonServiceResponse

import play.api.libs.json.{ Json, Writes }

case class TodoSequenceResponse(response: Seq[Todo]) extends JsonServiceResponse[Seq[Todo]] {
  override implicit def writes: Writes[Seq[Todo]] =
    (o: Seq[Todo]) =>
      Json.obj(
        "todos" -> response.map(_.toJson)
      ) ++ Todo.calculatePriorityAndMissingPriorityOccurrences(response)

  override def msg: String =
    "Todos"

  override def status: Int =
    200

  override def code: Int =
    200
}