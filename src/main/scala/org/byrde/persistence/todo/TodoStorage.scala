package org.byrde.persistence.todo

import org.byrde.models.Todo
import org.byrde.models.Todo.TodoId

import scala.concurrent.{ ExecutionContext, Future }

trait TodoStorage {
  def create(todo: Todo)(implicit ec: ExecutionContext): Future[Todo]

  def update(todoId: TodoId, todo: Todo)(implicit ec: ExecutionContext): Future[Todo]

  def remove(todoId: TodoId)(implicit ec: ExecutionContext): Future[Todo]

  def fetch(todoId: TodoId)(implicit ec: ExecutionContext): Future[Todo]

  def fetchAll(implicit ec: ExecutionContext): Future[Seq[Todo]]
}
