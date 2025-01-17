name := "lengine-compiler"

enablePlugins(JavaAppPackaging)

mainClass := Some("co.gyeongmin.lisp.compile.Main")

scalacOptions := Seq(
  "-deprecation"
)

assembly / assemblyJarName := "lenginec.jar"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.17" % "test"
libraryDependencies += "org.ow2.asm" % "asm" % "9.3"

Test / classLoaderLayeringStrategy := ClassLoaderLayeringStrategy.Flat
