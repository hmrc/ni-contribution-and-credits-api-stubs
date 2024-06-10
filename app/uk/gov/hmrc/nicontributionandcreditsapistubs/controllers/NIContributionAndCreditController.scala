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
import play.api.libs.json.Json
import play.api.mvc.{Action, AnyContent, ControllerComponents}
import uk.gov.hmrc.nicontributionandcreditsapistubs.models.{Failure, NIContribution, NICredit, Response}
import uk.gov.hmrc.play.bootstrap.backend.controller.BackendController

import javax.inject.{Inject, Singleton}
import scala.collection.mutable
import scala.concurrent.Future

@Singleton()
class NIContributionAndCreditController @Inject()(cc: ControllerComponents)
  extends BackendController(cc) with Logging {

  def contributionsAndCredits(nationalInsuranceNumber: String,
                              startTaxYear: Int,
                              endTaxYear: Int): Action[AnyContent] = Action.async { implicit request =>
    nationalInsuranceNumber match {
      case "SS000200" =>
        Future.successful(Ok(greenPathResponse))
      case "SS000400" =>
        Future.successful(BadRequest(failurePathResponse))
      case "SS000404" =>
        Future.successful(NotFound)
      case "SS000500" =>
        Future.successful(InternalServerError)
      case _ =>
        Future.successful(NotImplemented)
    }
    //    if (request.hasBody) {
    //      Future.successful(Ok(greenPathResponse))
    //    } else {
    //      Future.successful(UnprocessableEntity("No DoB Given"))
    //    }
  }

  private val builder = Json.newBuilder
  private val nIContributionsList = new mutable.ListBuffer[NIContribution]()
  private val nICreditList = new mutable.ListBuffer[NICredit]()

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

  builder += ("niContribution" -> nIContributionsList)
  builder += ("niCredit" -> nICreditList)

  private val greenPathResponse = builder.result()

  builder.clear()

  private val failureList = new mutable.ListBuffer[Failure]()
  failureList += new Failure("HTTP message not readable", "")
  failureList += new Failure("Constraint Violation - Invalid/Missing input parameter", "BAD_REQUEST")

  builder += ("failureList" -> failureList)

  private val failurePathResponse = builder.result()

}
