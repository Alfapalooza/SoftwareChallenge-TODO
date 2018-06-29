package org.byrde.models.todo

import play.api.libs.json.{ JsObject, Json }

case class Todos(all: Seq[Todo]) {
  lazy val priorityOccurrences: Map[Int, Int] =
    all
      .groupBy(_.priority)
      .mapValues(_.size)

  lazy val missingPriorityOccurrences: Seq[Int] =
    (0 to Todo.MaxPriority)
      .flatMap { i =>
        priorityOccurrences
          .get(i)
          .fold(Option(i))(_ => Option.empty[Int])
      }

  lazy val toJson: JsObject =
    Json.obj(
      "todos" -> all.map(_.toJson),
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
