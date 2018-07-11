import Dependencies._

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "com.example",
      scalaVersion := "2.12.2",
      version      := "0.1.0-SNAPSHOT"
    )),
    name := "Hello",
    //libraryDependencies += scalaTest % Test
    libraryDependencies += "org.scalatest" % "scalatest_2.12" % "3.0.1" % "test"
    //resolvers += "Artima Maven Repository" at "http://repo.artima.com/releases"

  )
