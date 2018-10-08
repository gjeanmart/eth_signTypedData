name := "TypedDataSignature"

organization := "net.consensys.tools.ethereum"

version := "0.0.1-SNAPSHOT"

scalaVersion := "2.12.7"

scmInfo := Some(
  ScmInfo(
    url(s"https://github.com/gjeanmart/eth_signTypedData"),
    s"git@github.com:gjeanmart/eth_signTypedData.git"
  )
)

cancelable := true

developers := List(
  Developer("gjeanmart",
            "Gregoire Jeanmart",
            "gregoire.jeanmart@gmail.com",
            url("https://gjeanmart.github.io")
  )
)

// define the statements initially evaluated when entering 'console', 'console-quick', but not 'console-project'
initialCommands in console := """
                                |""".stripMargin

javacOptions ++= Seq(
  "-Xlint:deprecation",
  "-Xlint:unchecked",
  "-source", "1.8",
  "-target", "1.8",
  "-g:vars"
)

lazy val web3JVersion = "3.6.0"
lazy val lombokVersion = "1.16.20"
libraryDependencies ++= Seq(
  "org.web3j" % "core" % web3JVersion,
  "org.projectlombok" % "lombok" % lombokVersion,
  "org.slf4j" % "slf4j-api" % "1.7.25",
  "org.slf4j" % "slf4j-simple" % "1.7.25",
  "junit" % "junit" % "4.12" % "test",
  "org.hamcrest" % "hamcrest-library" % "1.3" % "test",
  "junit"             %  "junit"       % "4.12"  % Test
)

// If you want to apply a license, such as the Apache 2 license, uncomment the following:
//licenses += ("Apache-2.0", url("https://www.apache.org/licenses/LICENSE-2.0.html"))

logLevel := Level.Warn

// Only show warnings and errors on the screen for compilations.
// This applies to both test:compile and compile and is Info by default
logLevel in compile := Level.Warn

// Level.INFO is needed to see detailed output when running tests
logLevel in test := Level.Info

