

version := "0.1"

scalaVersion := "2.13.6"

lazy val akkaVersion = "2.6.17"
lazy val akkaHttpVersion = "10.2.6"
lazy val logBackVersion = "1.2.6"
//lazy val akkaHttpJsonSerializersVersion = "1.34.0"
lazy val akkaHttpJsonSerializersVersion = "1.38.2"
val circeVersion = "0.14.1"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-stream" % akkaVersion,
  "com.typesafe.akka" %% "akka-actor-typed" % akkaVersion,
  "com.typesafe.akka" %% "akka-actor-testkit-typed" % akkaVersion % Test,
  "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
  "ch.qos.logback" % "logback-classic" % logBackVersion,
  "org.scalatest" %% "scalatest" % "3.2.9" % Test,
  //
  "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpVersion,
  "de.heikoseeberger" %% "akka-http-circe" % akkaHttpJsonSerializersVersion,
  "de.heikoseeberger" %% "akka-http-jackson" % akkaHttpJsonSerializersVersion,
  //
  "io.circe" %% "circe-core" % circeVersion,
  "io.circe" %% "circe-generic" % circeVersion,
  "io.circe" %% "circe-parser" % circeVersion
)