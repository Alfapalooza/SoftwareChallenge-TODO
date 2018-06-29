package org.byrde.configuration

import com.google.inject.Inject
import com.typesafe.config.{ Config, ConfigFactory }

import akka.util.Timeout

import scala.concurrent.duration._
import scala.language.postfixOps

class Configuration @Inject() () {
  lazy val underlyingConfig: Config =
    ConfigFactory.load().resolve()

  lazy val server: Config =
    underlyingConfig.getConfig("akka.server")

  lazy val name: String =
    server.getString("name")

  lazy val interface: String =
    server.getString("interface")

  lazy val port: Int =
    server.getInt("port")

  lazy val timeout: Timeout =
    Timeout(server.getInt("timeout") seconds)

  lazy val corsConfiguration: CORSConfiguration =
    CORSConfiguration(
      server.getString("cors.origins").split(", "),
      server.getString("cors.methods").split(", "),
      server.getString("cors.headers").split(", ")
    )
}
