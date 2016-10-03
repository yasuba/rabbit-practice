name := "finatraTest"

version := "1.0"

scalaVersion := "2.11.8"

resolvers += "Twitter" at "http://maven.twttr.com"

val versions = new {
  val finatra = "2.2.0"
  val guice = "4.0"
  val json4s = "3.4.0"
  val junit = "4.12"
  val scalatest = "2.2.4"
  val mockito = "1.10.19"
  val specs2 = "2.3.12"
  val sstone = "1.4"
  val akka = "2.3.11"
}

libraryDependencies += "com.typesafe.akka" %% "akka-actor" % versions.akka
libraryDependencies += "com.github.sstone" %% "amqp-client" % versions.sstone

libraryDependencies += "com.twitter" %% "finatra-http" % versions.finatra

libraryDependencies += "com.typesafe" % "config" % "1.3.0"

libraryDependencies += "org.json4s" %% "json4s-native" % versions.json4s

libraryDependencies += "com.twitter" %% "finatra-http" % versions.finatra % "test"
libraryDependencies += "com.twitter" %% "inject-server" % versions.finatra % "test"
libraryDependencies += "com.twitter" %% "inject-app" % versions.finatra % "test"
libraryDependencies += "com.twitter" %% "inject-core" % versions.finatra % "test"
libraryDependencies += "com.twitter" %% "inject-modules" % versions.finatra % "test"
libraryDependencies += "com.google.inject.extensions" % "guice-testlib" % versions.guice % "test"
libraryDependencies += "com.twitter" %% "finatra-jackson" % versions.finatra % "test"

libraryDependencies += "com.twitter" %% "finatra-http" % versions.finatra % "test" classifier "tests"
libraryDependencies += "com.twitter" %% "inject-server" % versions.finatra % "test" classifier "tests"
libraryDependencies += "com.twitter" %% "inject-app" % versions.finatra % "test" classifier "tests"
libraryDependencies += "com.twitter" %% "inject-core" % versions.finatra % "test" classifier "tests"
libraryDependencies += "com.twitter" %% "inject-modules" % versions.finatra % "test" classifier "tests"
libraryDependencies += "com.google.inject.extensions" % "guice-testlib" % versions.guice % "test" classifier "tests"
libraryDependencies += "com.twitter" %% "finatra-jackson" % versions.finatra % "test" classifier "tests"


// https://mvnrepository.com/artifact/org.mockito/mockito-all
libraryDependencies += "org.mockito" % "mockito-all" % versions.mockito % "test"

libraryDependencies += "junit" % "junit" % versions.junit % "test"

libraryDependencies += "org.scalatest" %% "scalatest" % versions.scalatest % "test"
libraryDependencies += "org.specs2" %% "specs2" % versions.specs2 % "test"