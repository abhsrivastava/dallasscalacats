name := "dallasscalacats"
version := "0.0.1-SNAPSHOT"
scalaVersion := "2.12.3"

scalacOptions ++= Seq(
  "-Ypartial-unification" // allow the compiler to unify type constructors of different arities
)

libraryDependencies ++= Seq(
  "org.typelevel" %% "cats-core" % "1.1.0",
  "joda-time" % "joda-time" % "2.10"
)
