import sbt._

object AppDependencies {

  private val bootstrapVersion = "9.11.0"
  private val playVersion      = "play-30"

  val compile: Seq[ModuleID] = Seq(
    "uk.gov.hmrc" %% s"bootstrap-backend-$playVersion" % bootstrapVersion
  )

  val test: Seq[ModuleID] = Seq(
    "uk.gov.hmrc" %% s"bootstrap-test-$playVersion" % bootstrapVersion % Test
  )

  val it: Seq[ModuleID] = Seq.empty
}
