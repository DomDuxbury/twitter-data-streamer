name := """data-collector"""

version := "1.0"

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  "org.scalikejdbc" %% "scalikejdbc" % "2.4.2",
  "postgresql" % "postgresql" % "9.1-901-1.jdbc4",
  "ch.qos.logback"  %  "logback-classic"   % "1.1.7",
  "org.scalatest" %% "scalatest" % "2.2.4" % "test",
  "org.twitter4j" % "twitter4j-stream" % "3.0.3",
  "io.spray" %%  "spray-json" % "1.3.2"
)


traceLevel in run := 0

fork in run := true

scalacOptions ++= Seq("-optimize")

mainClass in Compile := Some("app.dataCollector")
