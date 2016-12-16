name := "rtb-exchange"

organization := "com.bitworks"

version := "1.0.0-SNAPSHOT"

scalaVersion := "2.11.8"

scalacOptions in(Compile, doc) ++= Seq("-doc-title", "Bitworks RTB Exchange")
scalacOptions in(Compile, doc) ++= Seq("-doc-version", version.value)
scalacOptions in(Compile, doc) ++= Seq("-doc-root-content", "rootdoc.txt")
scalacOptions in(Compile, doc) ++= Seq("-skip-packages", "akka")

libraryDependencies ++= Seq(
  "com.fasterxml.jackson.core" % "jackson-databind" % "2.8.4",
  "org.postgresql" % "postgresql" % "9.4.1211",
  "io.getquill" % "quill-jdbc_2.11" % "1.0.0",
  "org.scaldi" % "scaldi-akka_2.11" % "0.5.8",
  "com.typesafe.akka" % "akka-actor_2.11" % "2.4.12",
  "com.typesafe.akka" % "akka-slf4j_2.11" % "2.4.12",
  "com.typesafe.akka" %% "akka-http-experimental" % "2.4.11",
  "ch.qos.logback" % "logback-classic" % "1.1.7"
)

libraryDependencies ++= Seq(
  "org.scalatest" % "scalatest_2.11" % "3.0.0" % "test",
  "org.dbunit" % "dbunit" % "2.5.3" % "test",
  "org.scalamock" %% "scalamock-scalatest-support" % "3.3.0" % "test",
  "com.typesafe.akka" % "akka-testkit_2.11" % "2.4.12" % "test",
  "org.easymock" % "easymock" % "3.4" % "test",
  "com.github.tomakehurst" % "wiremock" % "2.3.1" % "test"
)

fork in Test := true
javaOptions in Test += "-Dconfig.resource=application.test.conf"


assemblyJarName := s"${name.value}-${version.value}-jar-with-dependencies.jar"
mainClass in assembly := Some("com.bitworks.rtb.application.RtbApplication")


credentials += Credentials(Path.userHome / ".ivy2" / ".credentials")
publishTo := {
  val nexus = "http://rtb-ci.z1.netpoint-dc.com:8081/nexus/content/repositories/"
  if (isSnapshot.value)
    Some("snapshots" at nexus + "bitworks-rtb-snapshot/")
  else
    Some("releases" at nexus + "bitworks-rtb/")
}

artifact in(Compile, assembly) := {
  val art = (artifact in(Compile, assembly)).value
  art.copy(`classifier` = Some("jar-with-dependencies"))
}

addArtifact(artifact in(Compile, assembly), assembly)
