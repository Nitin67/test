name := """pharm-assignment"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaWs,
  "javax.ws.rs" % "javax.ws.rs-api" % "2.0-m16",
  "org.mongodb" % "mongo-java-driver" % "3.4.2",
  "com.google.code.morphia" % "morphia" % "0.102",
  "junit" % "junit" % "4.12",
  "redis.clients" % "jedis" % "2.9.0",
  "org.mockito" % "mockito-all" % "1.10.19",
  "com.github.scala-incubator.io" % "scala-io-file_2.10" % "0.4.1"
)
