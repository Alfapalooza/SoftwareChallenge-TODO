package org.byrde.models.responses.todo

import org.byrde.models.Todo
import org.byrde.models.responses.JsonServiceResponse

import play.api.libs.json.Writes

case class TodoResponse(response: Todo) extends JsonServiceResponse[Todo] {
  override implicit def writes: Writes[Todo] =
    _.toJson

  override def msg: String =
    "Todo"

  override def status: Int =
    200

  override def code: Int =
    200
}
