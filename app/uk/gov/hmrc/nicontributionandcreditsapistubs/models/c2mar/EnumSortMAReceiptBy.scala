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

import play.api.libs.json._

sealed trait EnumSortMAReceiptBy

object EnumSortMAReceiptBy {

  case object NinoAscending                extends EnumSortMAReceiptBy
  case object NinoDescending               extends EnumSortMAReceiptBy
  case object DateOfFinalPaymentDescending extends EnumSortMAReceiptBy
  case object DateOfFinalPaymentAscending  extends EnumSortMAReceiptBy

  private val mapping: Map[String, EnumSortMAReceiptBy] = Map(
    "NINO ASCENDING"                   -> NinoAscending,
    "NINO DESCENDING"                  -> NinoDescending,
    "DATE OF FINAL PAYMENT DESCENDING" -> DateOfFinalPaymentDescending,
    "DATE OF FINAL PAYMENT ASCENDING"  -> DateOfFinalPaymentAscending
  )

  def fromString(value: String): Option[EnumSortMAReceiptBy] =
    mapping.get(value)

  implicit val reads: Reads[EnumSortMAReceiptBy] =
    Reads.StringReads.collect(JsonValidationError("Invalid sort value")) {
      case s if mapping.contains(s) => mapping(s)
    }

  implicit val writes: Writes[EnumSortMAReceiptBy] =
    Writes(e => JsString(mapping.collectFirst { case (k, v) if v == e => k }.get))

}
