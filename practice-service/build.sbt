organization := "com.example"
name := "practice"
version := "0.0.1-SNAPSHOT"
scalaVersion := "2.12.1"

val http4sVersion = "0.15.16a"
val logbackVersion = "1.2.3"
val buckyVersion = "1.0.0-RC4"
val circeVersion = "0.8.0"

resolvers += "Mavenrepository" at "https://repo1.maven.org/maven2/"

libraryDependencies ++= Seq(
 "org.http4s"            %% "http4s-dsl"              % http4sVersion,
 "org.http4s"            %% "http4s-blaze-server"     % http4sVersion,
 "org.http4s"            %% "http4s-blaze-client"     % http4sVersion,
 "ch.qos.logback"        %  "logback-classic"         % logbackVersion,
 "com.itv"               %% "bucky-core"              % buckyVersion,
 "com.itv"               %% "bucky-rabbitmq"          % buckyVersion,
 "com.itv"               %% "bucky-scalaz"            % buckyVersion,
 "com.github.pureconfig" %% "pureconfig"              % "0.7.0",
 "org.apache.qpid"       % "qpid-broker"              % "6.0.4",
 "org.slf4j"             % "slf4j-api"                % "1.7.21",
 "net.logstash.logback"  % "logstash-logback-encoder" % "4.7",
 "org.scalatest"         %% "scalatest"               % "3.0.1" % "test",
 "org.scalaz"            %% "scalaz-core"             % "7.2.13",
 "org.http4s"            %% "http4s-circe"            % http4sVersion,
 "io.circe"              %% "circe-core"              % circeVersion,
 "io.circe"              %% "circe-generic"           % circeVersion,
 "io.circe"              %% "circe-parser"            % circeVersion
)
