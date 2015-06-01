name := """fleet-client"""

version := "0.0.2"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaWs,
  "com.fasterxml.jackson.datatype" % "jackson-datatype-guava" % "2.5.1",
  "com.spotify" % "docker-client" % "2.7.19",
  "org.mousio" % "etcd4j" % "2.3.2",
  "org.easytesting" % "fest-assert" % "1.4"
)

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator
