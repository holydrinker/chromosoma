name := "data-generator"

version := "0.1"

scalaVersion := "2.12.11"

libraryDependencies += "com.typesafe"  % "config" % "1.4.0"
libraryDependencies += "org.scalameta" %% "munit" % "0.7.7" % Test
libraryDependencies += "org.apache.avro" % "avro" % "1.9.2"
libraryDependencies += "org.apache.spark" %% "spark-core" % "2.4.5"
libraryDependencies += "org.apache.spark" %% "spark-sql" % "2.4.5"

testFrameworks += new TestFramework("munit.Framework")
