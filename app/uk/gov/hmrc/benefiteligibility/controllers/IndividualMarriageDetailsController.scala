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
import uk.gov.hmrc.benefiteligibility.services.IndividualMarriageDetailsService
import uk.gov.hmrc.play.bootstrap.backend.controller.BackendController

import javax.inject.{Inject, Singleton}
import scala.concurrent.Future

@Singleton()
class IndividualMarriageDetailsController @Inject() (
    cc: ControllerComponents,
    individualMarriageDetailsService: IndividualMarriageDetailsService
) extends BackendController(cc)
    with Logging {

  def getIndividualMarriageDetails(
      identifier: String,
      searchStartYear: Option[Int],
      searchEndYear: Option[Int],
      latest: Option[String],
      sequence: Option[Int]
  ): Action[AnyContent] =
    Action.async {
      individualMarriageDetailsService.mapIdentifierToResponse(identifier)
    }

}
