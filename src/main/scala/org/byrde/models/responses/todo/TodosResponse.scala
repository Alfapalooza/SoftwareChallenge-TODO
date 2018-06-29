package org.byrde.models.responses.todo

import org.byrde.models.responses.JsonServiceResponse
import org.byrde.models.todo.Todos

import play.api.libs.json.Writes

case class TodosResponse(response: Todos) extends JsonServiceResponse[Todos] {
  override implicit val writes: Writes[Todos] =
    _.toJson

  override val msg: String =
    "Todos"

  override val status: Int =
    200

  override val code: Int =
    200
}