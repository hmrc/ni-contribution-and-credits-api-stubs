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

package uk.gov.hmrc.nicontributionandcreditsapistubs.models.c2mar.errors

import play.api.libs.json._

sealed trait ErrorCode400

object ErrorCode400 {

  private case object ConstraintViolation    extends ErrorCode400
  private case object HttpMessageNotReadable extends ErrorCode400

  private val mapping = Map(
    "400.1" -> ConstraintViolation,
    "400.2" -> HttpMessageNotReadable
  )

  implicit val reads: Reads[ErrorCode400] =
    Reads.StringReads.collect(JsonValidationError("Invalid 400 error code")) {
      case s if mapping.contains(s) => mapping(s)
    }

  implicit val writes: Writes[ErrorCode400] =
    Writes(c => JsString(mapping.collectFirst { case (k, v) if v == c => k }.get))

}
