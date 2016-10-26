name := "rtb-exchange"

version := "1.0"

scalaVersion := "2.11.8"

scalacOptions in (Compile,doc) ++= Seq("-doc-title", "Bitworks RTB Exchange")
scalacOptions in (Compile,doc) ++= Seq("-doc-version", version.value)
scalacOptions in (Compile,doc) ++= Seq("-doc-root-content", "rootdoc.txt")

libraryDependencies ++= Seq(
  "org.scalatest" % "scalatest_2.11" % "3.0.0" % "test",
  "com.fasterxml.jackson.module" % "jackson-module-scala_2.11" % "2.8.3"
)
