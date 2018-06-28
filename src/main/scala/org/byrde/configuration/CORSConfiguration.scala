package org.byrde.configuration

case class CORSConfiguration(origins: Seq[String], methods: Seq[String], headers: Seq[String])