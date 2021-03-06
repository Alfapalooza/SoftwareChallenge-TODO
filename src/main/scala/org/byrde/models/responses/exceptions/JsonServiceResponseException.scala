package org.byrde.models.responses.exceptions

import org.byrde.models.responses.DefaultJsonServiceResponse

class JsonServiceResponseException(val msg: String, val code: Int, val status: Int) extends Throwable(msg) with DefaultJsonServiceResponse {
  override def apply(message: String): JsonServiceResponseException =
    new JsonServiceResponseException(message, code, status)

  def apply(throwable: Throwable): JsonServiceResponseException =
    new JsonServiceResponseException(throwable.getMessage, code, status)
}

object JsonServiceResponseException {
  object E0400 extends JsonServiceResponseException("Bad Request", 400, 400)
  object E0500 extends JsonServiceResponseException("Internal Server Error", 500, 500)

  def apply(throwable: Throwable): JsonServiceResponseException =
    E0500(throwable)
}
