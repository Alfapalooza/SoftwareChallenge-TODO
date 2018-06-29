package org.byrde.persistence.todo

import org.byrde.models.todo.Todo.TodoId
import org.byrde.models.todo.{ Todo, Todos }

import scala.concurrent.{ ExecutionContext, Future }

trait TodoStorage {
  def create(todo: Todo)(implicit ec: ExecutionContext): Future[Todo]

  def edit(todoId: TodoId, todo: Todo)(implicit ec: ExecutionContext): Future[Todo]

  def remove(todoId: TodoId)(implicit ec: ExecutionContext): Future[Todo]

  def fetch(todoId: TodoId)(implicit ec: ExecutionContext): Future[Todo]

  def fetchAll(implicit ec: ExecutionContext): Future[Todos]
}
