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

package uk.gov.hmrc.nicontributionandcreditsapistubs.models.c2mar

import java.time.LocalDate
import play.api.libs.json._

final case class Class2MAReceiptDetails(
    initials: Option[String],
    surname: Option[String],
    receivablePeriodStartDate: Option[LocalDate],
    receivablePeriodEndDate: Option[LocalDate],
    receivablePayment: Option[BigDecimal],
    receiptDate: Option[LocalDate],
    liabilityStartDate: Option[LocalDate],
    liabilityEndDate: Option[LocalDate],
    billAmount: Option[BigDecimal],
    billScheduleNumber: Option[Int],
    isClosedRecord: Option[Boolean],
    weeksPaid: Option[Int]
)

object Class2MAReceiptDetails {
  implicit val format: OFormat[Class2MAReceiptDetails] = Json.format[Class2MAReceiptDetails]
}
