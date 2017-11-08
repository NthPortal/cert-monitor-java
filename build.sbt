scalaVersion := "2.12.4"
crossScalaVersions := Seq("2.11.11", "2.12.4")

val rawVersion = "0.1.0"
val sharedSettings = Seq(
  organization := "com.nthportal",
  name := "cert-monitor-scala",
  description := "Library for monitoring CertStream certificate issuance",

  isSnapshot := false,
  version := rawVersion + { if (isSnapshot.value) "-SNAPSHOT" else "" },

  scalaVersion := "2.12.4",

  resolvers += "jitpack" at "https://jitpack.io",

  libraryDependencies ++= Seq(
    "com.github.CaliDog" % "certstream-java" % "0.1",
    "org.scalatest" %% "scalatest" % "3.0.1+" % Test
  ),

  autoAPIMappings := true,

  scalacOptions ++= {
    if (isSnapshot.value) Seq()
    else scalaVersion.value split '.' map { _.toInt } match {
      case Array(2, 11, _) => Seq("-optimize")
      case Array(2, 12, patch) if patch <= 2 => Seq("-opt:l:project")
      case Array(2, 12, patch) if patch > 2 => Seq("-opt:l:inline")
      case _ => Seq()
    }
  },

  publishTo := {
    if (isSnapshot.value) Some("snapshots" at "https://oss.sonatype.org/content/repositories/snapshots")
    else None
  },

  publishMavenStyle := true,
  licenses := Seq("The Apache License, Version 2.0" -> url("https://www.apache.org/licenses/LICENSE-2.0.txt")),
  homepage := Some(url("https://github.com/NthPortal/cert-monitor-scala")),

  pomExtra := {
    <scm>
      <url>https://github.com/NthPortal/cert-monitor-scala</url>
      <connection>scm:git:git@github.com:NthPortal/cert-monitor-scala.git</connection>
      <developerConnection>scm:git:git@github.com:NthPortal/cert-monitor-scala.git</developerConnection>
    </scm>
      <developers>
        <developer>
          <id>NthPortal</id>
          <name>NthPortal</name>
          <url>https://github.com/NthPortal</url>
        </developer>
      </developers>
  }
)

lazy val certmon = project.in(file("."))
  .settings(sharedSettings)
