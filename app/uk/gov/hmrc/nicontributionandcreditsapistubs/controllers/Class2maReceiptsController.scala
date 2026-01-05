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
import play.api.mvc.{Action, AnyContent, ControllerComponents}
import uk.gov.hmrc.nicontributionandcreditsapistubs.models.c2mar.EnumSortMAReceiptBy
import uk.gov.hmrc.nicontributionandcreditsapistubs.services.Class2MaReceiptsService
import uk.gov.hmrc.play.bootstrap.backend.controller.BackendController

import javax.inject.{Inject, Singleton}

@Singleton()
class Class2maReceiptsController @Inject()(
    cc: ControllerComponents,
    class2MaReceiptsService: Class2MaReceiptsService
) extends BackendController(cc)
    with Logging {

  def class2maReceipts(identifier: String, latest: Option[Boolean], receiptDate: Option[String], sortBy : Option[String] ): Action[AnyContent] =
    Action.async {
      class2MaReceiptsService.statusMapping(
        identifier,
        latest,
        receiptDate,
        sortBy.flatMap(EnumSortMAReceiptBy.fromString)
      )
    }

}
