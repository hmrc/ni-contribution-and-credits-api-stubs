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
import uk.gov.hmrc.benefiteligibility.services.StubId.{AA000001A, AA000002A}
import uk.gov.hmrc.utils.JsonUtils

import javax.inject.Inject
import scala.concurrent.Future

class LiabilitySummaryService @Inject() (jsonUtils: JsonUtils) {

  def mapIdentifierToResponse(
      identifier: String
  ): Future[Result] =

    StubId.withName(identifier) match {
      case AA000001A =>
        Future.successful(
          BadRequest(
            jsonUtils.readJsonFile(
              s"conf/resources/data/jsons/liabilitySummary/ErrorResponse400.2.json"
            )
          )
        )
      case AA000002A =>
        Future.successful(
          Ok(
            jsonUtils.readJsonFile(
              s"conf/resources/data/jsons/liabilitySummary/SuccessResponse.json"
            )
          )
        )
      case _ =>
        Future.successful(
          BadRequest(
            jsonUtils.readJsonFile(
              s"conf/resources/data/jsons/liabilitySummary/ErrorResponseDefault.json"
            )
          )
        )
    }

}
