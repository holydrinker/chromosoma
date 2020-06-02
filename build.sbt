name := "data-generator"

version := "0.1"

scalaVersion := "2.12.11"

libraryDependencies += "com.typesafe"        % "config"           % "1.4.0"
libraryDependencies += "org.apache.avro"     % "avro"             % "1.9.2"
libraryDependencies += "com.sksamuel.avro4s" % "avro4s-core_2.12" % "3.1.1"

libraryDependencies += "org.scalameta" %% "munit"     % "0.7.7" % Test
libraryDependencies += "commons-io"    % "commons-io" % "2.7"   % Test

testFrameworks += new TestFramework("munit.Framework")
