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
import play.api.libs.json.{JsError, JsSuccess}
import play.api.mvc.{Action, AnyContent, ControllerComponents, Request}
import uk.gov.hmrc.nicontributionandcreditsapistubs.models.*
import uk.gov.hmrc.nicontributionandcreditsapistubs.services.NIContributionAndCreditService
import uk.gov.hmrc.play.bootstrap.backend.controller.BackendController

import javax.inject.{Inject, Singleton}

@Singleton()
class NIContributionAndCreditController @Inject() (
    cc: ControllerComponents,
    nIContributionAndCreditService: NIContributionAndCreditService
) extends BackendController(cc)
    with Logging {

  def contributionsAndCredits(nationalInsuranceNumber: String, startTaxYear: Int, endTaxYear: Int): Action[AnyContent] =
    Action.async { implicit request =>
      nIContributionAndCreditService.statusMapping(
        nationalInsuranceNumber,
        startTaxYear,
        endTaxYear,
        getDateOfBirthFromRequestBody(request).get
      )
    }

  def getDateOfBirthFromRequestBody(request: Request[AnyContent]): Option[NICCRequestPayload] =
    request.body.asJson match {
      case Some(json) =>
        json.validate[NICCRequestPayload] match {
          case JsSuccess(data, _) => Some(data)
          case JsError(_)         => None
        }
      case _ => None
    }

}
