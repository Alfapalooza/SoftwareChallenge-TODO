package org.byrde.logger

import play.api.libs.json.{ JsObject, JsString, Json }
import org.byrde.utils.OptionUtils._

import akka.http.scaladsl.model.{ HttpRequest, HttpResponse, IdHeader }
import akka.http.scaladsl.server.directives.HttpRequestWithEntity

trait LoggingInformation[-T] {
  def log(elem: T): JsObject

  def apply(elem: T): JsObject =
    log(elem)

  def log(msg: String, elem: T): JsObject =
    log(elem) + ("message" -> JsString(msg))

  def log(msg: JsObject, elem: T): JsObject =
    log(elem) + ("message" -> msg)
}

/**
 * Type classes for `LoggingInformation[T]`
 */
object LoggingInformation {
  implicit val httpRequestInformation: LoggingInformation[HttpRequest] =
    (req: HttpRequest) =>
      Json.obj(
        "id" -> req.header[IdHeader].fold("None")(header => s"${header.id}"),
        "uri" -> req.uri.toString,
        "method" -> req.method.value.toString,
        "headers" -> req.headers.map(header => s"${header.name}: ${header.value}"),
        "cookies" -> req.cookies.map(cookie => s"${cookie.name}: ${cookie.value}")
      )

  implicit val httpResponseInformation: LoggingInformation[HttpResponse] =
    (elem: HttpResponse) =>
      Json.obj(
        "status" -> elem.status.toString(),
        "headers" -> elem.headers.map(header => s"${header.name}: ${header.value}")
      )

  implicit val exceptionWithHttpRequest: LoggingInformation[(Throwable, HttpRequest)] =
    (elem: (Throwable, HttpRequest)) => {
      val (ex, req) =
        elem._1 -> elem._2

      def serializeException(ex: Throwable): JsObject = {
        def loop(throwable: Throwable): JsObject = {
          val causedBy =
            Option(throwable)
              .fold(Json.obj()) { cause =>
                Json.obj("causedBy" -> loop(cause.getCause))
              }

          Json.obj(
            "class" -> ex.getClass.getName(),
            "message" -> ex.getMessage,
            "stackTrace" -> ex.getStackTrace.map(_.toString)
          ) ++ causedBy
        }

        Json.obj(
          "class" -> ex.getClass.getName(),
          "message" -> ex.getMessage,
          "stackTrace" -> ex.getStackTrace.map(_.toString)
        ) ++ loop(ex.getCause)
      }

      httpRequestInformation(req) ++
        Json.obj(
          "message" -> ex.getMessage,
          "exception" -> serializeException(ex)
        )
    }

  implicit def httpRequestWithEntity[T]: LoggingInformation[HttpRequestWithEntity[T]] =
    (reqWithEntity: HttpRequestWithEntity[T]) => {
      val req =
        reqWithEntity.request

      val bodyOpt =
        if (reqWithEntity.body.toString.isEmpty)
          None
        else
          reqWithEntity.body.toString.?

      bodyOpt.fold(httpRequestInformation(req)) { body =>
        httpRequestInformation(req) ++ Json.obj("body" -> body)
      }
    }
}