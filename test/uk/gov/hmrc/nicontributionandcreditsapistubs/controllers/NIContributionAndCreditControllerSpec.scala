/*
 * Copyright 2024 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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

class NIContributionAndCreditControllerSpec extends AnyFreeSpec with GuiceOneAppPerSuite with OptionValues with ScalaFutures with should.Matchers with BeforeAndAfterEach {
  override def fakeApplication(): Application = GuiceApplicationBuilder().build()


  "Must return 200 when the request is valid, contains NINO with suffix and response is valid" in {
    val body = Json.obj("dateOfBirth" -> "1998-04-23")

    val url = "/nps/nps-json-service/nps/v1/api/national-insurance/BB000200B/contributions-and-credits/from/2010/to/2014"
    val request = FakeRequest("POST", url)
      .withHeaders(CONTENT_TYPE -> "application/json")
      .withJsonBody(body)

    val result = route(app, request).value.futureValue

    result.header.status should be(OK)
  }

  "Must return 200 when the request is valid, contains NINO without suffix and response is valid" in {
    val body = Json.obj("dateOfBirth" -> "1998-04-23")

    val url = "/nps/nps-json-service/nps/v1/api/national-insurance/BB000200/contributions-and-credits/from/2010/to/2014"
    val request = FakeRequest("POST", url)
      .withHeaders(CONTENT_TYPE -> "application/json")
      .withJsonBody(body)

    val result = route(app, request).value.futureValue

    result.header.status should be(OK)
  }

  "Must return 400 when the request NI ends with 400" in {
    val body = Json.obj("dateOfBirth" -> "1998-04-23")

    val url = "/nps/nps-json-service/nps/v1/api/national-insurance/BB000400B/contributions-and-credits/from/2010/to/2014"
    val request = FakeRequest("POST", url)
      .withHeaders(CONTENT_TYPE -> "application/json")
      .withJsonBody(body)

    val result = route(app, request).value.futureValue

    result.header.status should be(BAD_REQUEST)
  }
}