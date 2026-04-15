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

package uk.gov.hmrc.benefiteligibility.services

import play.api.mvc.Result
import play.api.mvc.Results.{BadRequest, Ok}
import uk.gov.hmrc.benefiteligibility.services.StubId.{S2123456B, S2345678C}
import uk.gov.hmrc.utils.JsonUtils

import javax.inject.Inject
import scala.concurrent.Future

class BenefitSchemeDetailsService @Inject() (jsonUtils: JsonUtils) {

  def mapIdentifierToResponse(
      scon: String
  ): Future[Result] =

    StubId.withNameOption(scon) match {
      case Some(S2123456B) =>
        Future.successful(
          Ok(
            jsonUtils.readJsonFile(
              s"conf/resources/data/jsons/benefitSchemeDetails/SuccessResponse.json"
            )
          )
        )
      case Some(S2345678C) =>
        Future.successful(
          Ok(
            jsonUtils.readJsonFile(
              s"conf/resources/data/jsons/benefitSchemeDetails/MinimalSuccessResponse.json"
            )
          )
        )
      case _ =>
        Future.successful(
          BadRequest(
            jsonUtils.readJsonFile(
              s"conf/resources/data/jsons/benefitSchemeDetails/ErrorResponse400.2.json"
            )
          )
        )
    }

}
