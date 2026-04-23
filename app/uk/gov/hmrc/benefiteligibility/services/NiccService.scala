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
import play.api.mvc.Results.{BadRequest, Forbidden, InternalServerError, NotFound, Ok}
import uk.gov.hmrc.benefiteligibility.models.NiccRequest
import uk.gov.hmrc.benefiteligibility.services.StubId.*
import uk.gov.hmrc.utils.JsonUtils

import javax.inject.Inject
import scala.concurrent.Future

class NiccService @Inject() (jsonUtils: JsonUtils) {

  def mapIdentifierToResponse(
      niccRequest: NiccRequest
  ): Future[Result] =

    StubId.withNameOption(niccRequest.nationalInsuranceNumber) match {
      case Some(AA000006) =>
        Future.successful(
          Forbidden(jsonUtils.readJsonFile(s"conf/resources/data/jsons/nicc/ErrorResponse403.json"))
        )
      case Some(AA000002A | AA000002 | AA000004 | GK938415) =>
        Future.successful(
          Ok(jsonUtils.readJsonFile(s"conf/resources/data/jsons/nicc/Class1andClass2SuccessResponse.json"))
        )
      case Some(AA000003 | AA000008) =>
        Future.successful(
          Ok(jsonUtils.readJsonFile(s"conf/resources/data/jsons/nicc/Class2SuccessResponse.json"))
        )
      case Some(AA000005) =>
        Future.successful(
          Ok(jsonUtils.readJsonFile(s"conf/resources/data/jsons/nicc/MinimalSuccessResponse.json"))
        )
      case Some(AA000001A) =>
        Future.successful(
          BadRequest(jsonUtils.readJsonFile(s"conf/resources/data/jsons/nicc/ErrorResponse400.2.json"))
        )
      case Some(AA000007) =>
        Future.successful(
          NotFound(jsonUtils.readJsonFile(s"conf/resources/data/jsons/nicc/ErrorResponse400.2.json"))
        )
      case Some(AA000009) =>
        Future.successful(
          InternalServerError(jsonUtils.readJsonFile(s"conf/resources/data/jsons/nicc/ErrorResponse500.json"))
        )
      case _ =>
        Future.successful(
          BadRequest(jsonUtils.readJsonFile(s"conf/resources/data/jsons/DefaultNpsError400.json"))
        )

    }

}
