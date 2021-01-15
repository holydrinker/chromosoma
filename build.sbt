name := "chromosoma"

version := "0.1.0"

scalaVersion := "2.12.11"

libraryDependencies ++= List(
  "com.typesafe"          % "config"          % "1.4.0",
  "org.apache.avro"       % "avro"            % "1.9.2",
  "com.sksamuel.avro4s"   %% "avro4s-core"    % "3.1.1",
  "io.spray"              %% "spray-json"     % "1.3.5",
  "com.github.pureconfig" %% "pureconfig"     % "0.12.3",
  "commons-io"            % "commons-io"      % "2.7",
  "io.circe"              %% "circe-parser"   % "0.13.0",
  "io.circe"              %% "circe-generic"  % "0.13.0",
  "ch.qos.logback"        % "logback-classic" % "1.2.3",
  "org.scalameta"         %% "munit"          % "0.7.7" % Test
)

testFrameworks += new TestFramework("munit.Framework")

assemblyMergeStrategy in assembly := {
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case x                             => MergeStrategy.first
}
