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

import play.api.Logging
import play.api.libs.json.{JsObject, Json}
import play.api.mvc.{Action, AnyContent, ControllerComponents}
import uk.gov.hmrc.nicontributionandcreditsapistubs.models.{Failure, NIContribution, NICredit, Response}
import uk.gov.hmrc.play.bootstrap.backend.controller.BackendController

import javax.inject.{Inject, Singleton}
import scala.collection.mutable
import scala.concurrent.Future

@Singleton()
class NIContributionAndCreditController @Inject()(cc: ControllerComponents)
  extends BackendController(cc) with Logging {

  private val builder = Json.newBuilder

  def contributionsAndCredits(nationalInsuranceNumber: String,
                              startTaxYear: Int,
                              endTaxYear: Int): Action[AnyContent] = Action.async { implicit request =>
    nationalInsuranceNumber match {
      case "BB000200B" | "BB000200" =>
        val nIContributionsList = new mutable.ListBuffer[NIContribution]()
        val nICreditList = new mutable.ListBuffer[NICredit]()

        nIContributionsList += new NIContribution(2022,
          "s",
          "(NONE)",
          99999999999999.98,
          99999999999999.98,
          "COMPLIANCE & YIELD INCOMPLETE",
          99999999999999.98)

        nICreditList += new NICredit(2022,
          53,
          "C2",
          "CLASS 2 - NORMAL RATE",
          99999999999999.98,
          99999999999999.98,
          "NOT KNOWN/NOT APPLICABLE")

        Future.successful(Ok(buildSuccessfulResponse(nIContributionsList, nICreditList)))
      case "BB000400B" =>

        val origin = "HIP"
        val response = new Response(
          Seq(Failure("HTTP message not readable", ""), Failure("Constraint Violation - Invalid/Missing input parameter", "BAD_REQUEST"))
        )

        Future.successful(BadRequest(buildErrorResponse(origin, response)))
      case "BB000401B" =>
        val failuresList = new mutable.ListBuffer[Failure]()
        failuresList += new Failure("Unauthorised", "Invalid Credentials")

        Future.successful(Unauthorized(buildFailuresResponse(failuresList)))
      case "BB000403B" =>
        val failure = Json.toJson(new Failure("Forbidden", "403.2"))

        Future.successful(Unauthorized(failure))
      case "BB000404B" =>
        Future.successful(NotFound)
      case "BB000422B" =>
        val failuresList = new mutable.ListBuffer[Failure]()
        failuresList += new Failure("Start tax year after end tax year", "63496")

        Future.successful(UnprocessableEntity(buildFailuresResponse(failuresList)))
      case "BB000503B" =>

        val origin = "HIP"
        val response = new Response(
          Seq(Failure("HTTP message not readable", ""), Failure("Constraint Violation - Invalid/Missing input parameter", "BAD_REQUEST"))
        )

        Future.successful(ServiceUnavailable(buildErrorResponse(origin, response)))
      case _ =>
        Future.successful(InternalServerError)
    }
  }

  private def buildSuccessfulResponse(niContributionsList: mutable.ListBuffer[NIContribution],
                                      nICreditList: mutable.ListBuffer[NICredit]): JsObject = {
    builder.clear()

    builder += ("niContribution" -> niContributionsList)
    builder += ("niCredit" -> nICreditList)

    builder.result()
  }

  private def buildFailuresResponse(failuresList: mutable.ListBuffer[Failure]): JsObject = {
    builder.clear()
    builder += ("failures" -> failuresList)
    builder.result()
  }

  private def buildErrorResponse(origin: String, failuresResponse: Response): JsObject = {
    builder.clear()
    builder += ("origin" -> origin)
    builder += ("response" -> failuresResponse)
    builder.result()
  }

}