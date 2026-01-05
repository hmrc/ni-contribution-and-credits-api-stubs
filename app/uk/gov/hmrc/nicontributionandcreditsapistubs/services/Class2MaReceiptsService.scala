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

package uk.gov.hmrc.nicontributionandcreditsapistubs.services

import play.api.mvc.Result
import play.api.mvc.Results.Ok
import uk.gov.hmrc.nicontributionandcreditsapistubs.models.c2mar.EnumSortMAReceiptBy
import uk.gov.hmrc.nicontributionandcreditsapistubs.utils.JsonUtils

import javax.inject.Inject
import scala.concurrent.Future

class Class2MaReceiptsService @Inject() (jsonUtils: JsonUtils) {

  def statusMapping(
      identifier: String,
      latest: Option[Boolean],
      receiptDate: Option[String],
      sortBy: Option[EnumSortMAReceiptBy]
  ): Future[Result] =

    Future.successful(Ok(jsonUtils.readJsonFile(s"conf/resources/data/jsons/c2mar/MA202512.json")))

}
