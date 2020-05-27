name := "data-generator"

version := "0.1"

scalaVersion := "2.13.2"

libraryDependencies += "com.typesafe"  % "config" % "1.4.0"
libraryDependencies += "org.scalameta" %% "munit" % "0.7.7" % Test
testFrameworks += new TestFramework("munit.Framework")
