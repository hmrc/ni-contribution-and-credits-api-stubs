import sbt._

object AppDependencies {

  private val bootstrapVersion = "10.1.0"
  private val playVersion =      "play-30"

  val compile: Seq[ModuleID] = Seq(
    "uk.gov.hmrc" %% s"bootstrap-backend-$playVersion" % bootstrapVersion,
    "com.beachape" %% "enumeratum-play-json" % "1.9.2"
  )

  val test: Seq[ModuleID] = Seq(
    "uk.gov.hmrc" %% s"bootstrap-test-$playVersion" % bootstrapVersion % Test,
    "com.beachape" %% "enumeratum-play-json" % "1.9.2" % Test
  )

  val it: Seq[ModuleID] = Seq.empty
}
