package org.byrde.models.responses.exceptions

import org.byrde.models.Todo.TodoId

import scala.util.control.NoStackTrace

case class TodoNotFoundException(id: TodoId) extends JsonServiceResponseException(s"Todo not found: $id", 404, 404) with NoStackTrace