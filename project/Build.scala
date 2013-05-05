import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {
  val appName         = "Listig"
  val appVersion      = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    jdbc,
    "com.typesafe.play" %% "play-slick" % "0.3.2",
    "mysql" % "mysql-connector-java" % "5.1.24"
  )

  val main = play.Project(appName, appVersion, appDependencies).settings()
}
