name := "neo4play"

version := "1.0"

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"

scalaVersion := "2.10.3"

libraryDependencies ++= Seq(
//  "net.liftweb" % "lift-json_2.9.1" % "2.6-M2",
//  "org.specs2" %%  "specs2" % "2.3.8" % "test",
//  "joda-time" % "joda-time" % "2.3",
//  "org.joda" % "joda-convert" % "1.6",
//  "commons-codec" % "commons-codec" % "1.9",
  "com.typesafe.play" %% "play" % "2.2.3"
)
    