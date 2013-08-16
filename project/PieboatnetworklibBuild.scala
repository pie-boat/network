import sbt._
import sbt.Keys._

object PieboatnetworklibBuild extends Build {

  lazy val pieboatnetworklib = Project(
    id = "pieboat-network-lib",
    base = file("."),
    settings = Project.defaultSettings ++ Seq(
      name := "Pieboat-Network-lib",
      organization := "pieboat.network",
      version := "0.1-SNAPSHOT",
      scalaVersion := "2.10.2"
      // add other settings here
    )
  )
}
