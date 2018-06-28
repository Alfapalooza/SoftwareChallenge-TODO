package org.byrde.models.responses

import org.byrde.utils.WritesUtils

import play.api.libs.json.Writes

trait DefaultJsonServiceResponse extends JsonServiceResponse[String] {
  self =>
  def apply(message: String): DefaultJsonServiceResponse =
    new DefaultJsonServiceResponse {
      override def msg: String =
        message

      override def code: Int =
        self.code

      override def status: Int =
        self.status
    }

  override implicit val writes: Writes[String] =
    WritesUtils.string

  override val response: String =
    msg
}
