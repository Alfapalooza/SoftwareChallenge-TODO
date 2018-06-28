package org.byrde.persistence.todo.impl

import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicLong

import org.byrde.models.Todo
import org.byrde.models.Todo.TodoId
import org.byrde.models.responses.exceptions.TodoNotFound
import org.byrde.persistence.todo.TodoStorage

import scala.concurrent._
import scala.collection.JavaConverters._

object TodoMemoryStorage extends TodoStorage {
  private lazy val counter: AtomicLong =
    new AtomicLong()

  private lazy val todos =
    new ConcurrentHashMap[TodoId, Todo].asScala

  override def create(todo: Todo)(implicit ec: ExecutionContext) =
    Future {
      val todoWithNewId =
        todo(counter.getAndIncrement())

      todos
        .putIfAbsent(todoWithNewId.id, todoWithNewId)
        .getOrElse(todoWithNewId)
    }

  override def update(todoId: TodoId, todo: Todo)(implicit ec: ExecutionContext) =
    Future {
      todos
        .putIfAbsent(todoId, todo)
        .getOrElse(todo)
    }

  override def remove(todoId: TodoId)(implicit ec: ExecutionContext) =
    Future {
      val todo =
        todos
          .getOrElse(todoId, throw TodoNotFound(todoId))

      todos
        .remove(todoId)

      todo
    }

  override def fetch(todoId: TodoId)(implicit ec: ExecutionContext) =
    Future {
      todos
        .getOrElse(todoId, throw TodoNotFound(todoId))
    }

  override def fetchAll(implicit ec: ExecutionContext): Future[Seq[Todo]] =
    Future {
      todos
        .values
        .toSeq
    }
}
