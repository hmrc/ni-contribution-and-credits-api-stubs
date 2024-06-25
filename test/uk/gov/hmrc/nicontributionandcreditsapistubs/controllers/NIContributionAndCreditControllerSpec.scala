package uk.gov.hmrc.nicontributionandcreditsapistubs.controllers

import org.scalatest.concurrent.ScalaFutures
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should
import org.scalatest.{BeforeAndAfterEach, OptionValues}
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.Application
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.libs.json.Json
import play.api.test.FakeRequest
import play.api.test.Helpers._
import uk.gov.hmrc.http.HttpResponse
import uk.gov.hmrc.nicontributionandcreditsapistubs.models.{NICCResponse, NIContribution}

class NIContributionAndCreditControllerSpec extends AnyFreeSpec with GuiceOneAppPerSuite with OptionValues with ScalaFutures with should.Matchers with BeforeAndAfterEach {
  override def fakeApplication(): Application = GuiceApplicationBuilder().build()

  "return 200 when the request is valid and response is valid" in {
    val body = Json.obj("dateOfBirth" -> "1998-04-23")


    val expectedResponseObject: HttpResponse = HttpResponse.apply(
      status = 200,
      json = Json.toJson(NICCResponse(
        Seq(NIContribution(2018, "A", "A", BigDecimal(1), BigDecimal(1), "A", BigDecimal(1))),
        Seq(),
      )),
      headers = Map.empty
    )


    val url = "/nps-json-service/nps/v1/api/national-insurance/BB000200B/contributions-and-credits/from/2010/to/2014"
    val request = FakeRequest("POST", url)
      .withHeaders(CONTENT_TYPE -> "application/json")
      .withJsonBody(body)

    val result = route(app, request).value.futureValue

    result.header.status should be(OK)

  }
}
