package org.byrde.models.responses.exceptions

import org.byrde.models.Todo.TodoId

import scala.util.control.NoStackTrace

case class TodoNotFound(id: TodoId) extends Exception(s"Todo not found: $id") with NoStackTrace
