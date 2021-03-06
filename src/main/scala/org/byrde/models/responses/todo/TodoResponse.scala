package org.byrde.models.responses.todo

import org.byrde.models.responses.JsonServiceResponse
import org.byrde.models.todo.Todo

import play.api.libs.json.Writes

case class TodoResponse(response: Todo) extends JsonServiceResponse[Todo] {
  override implicit val writes: Writes[Todo] =
    _.toJson

  override val msg: String =
    "Todo"

  override val status: Int =
    200

  override val code: Int =
    200
}
