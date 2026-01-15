/*
 * Copyright 2026 HM Revenue & Customs
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

package uk.gov.hmrc.benefiteligibility.controllers

import play.api.Logging
import play.api.libs.json.{JsError, JsSuccess}
import play.api.mvc.{Action, AnyContent, ControllerComponents, Request}
import uk.gov.hmrc.benefiteligibility.models.NICCRequest
import uk.gov.hmrc.benefiteligibility.services.NICCService
import uk.gov.hmrc.play.bootstrap.backend.controller.BackendController

import javax.inject.{Inject, Singleton}

@Singleton()
class NICCController @Inject() (
    cc: ControllerComponents,
    niccService: NICCService
) extends BackendController(cc)
    with Logging {

  def getNICCDetails: Action[AnyContent] =
    Action.async { implicit request =>
      niccService.mapIdentifierToResponse(
        getNationalInsuranceNumberFromRequestBody(request).get
      )
    }

  private def getNationalInsuranceNumberFromRequestBody(request: Request[AnyContent]): Option[NICCRequest] =
    request.body.asJson match {

      case Some(json) =>
        json.validate[NICCRequest] match {
          case JsSuccess(data, _) =>
            logger.info(
              s"NICCRequest parsed successfully: " +
                s"NINO=${data.nationalInsuranceNumber}, " +
                s"DOB=${data.dateOfBirth}, " +
                s"TaxYear=${data.startTaxYear}-${data.endTaxYear}"
            )
            Some(data)
          case JsError(_) => None
        }
      case _ => None
    }

}
