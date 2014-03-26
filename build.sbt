name := "ms"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
  "org.postgresql" % "postgresql" % "9.3-1101-jdbc41",
  "commons-io" % "commons-io" % "2.3"
)     

play.Project.playJavaSettings
