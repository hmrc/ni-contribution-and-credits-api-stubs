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

sealed trait ErrorResponse403

object ErrorResponse403 {

  final case class Forbidden(
      reason: String = "Forbidden",
      code: String = "403.2"
  ) extends ErrorResponse403

  final case class UserNotAuthorised(
      reason: String = "User Not Authorised",
      code: String = "403.1"
  ) extends ErrorResponse403

  implicit val forbiddenFormat: OFormat[Forbidden] =
    Json.format[Forbidden]

  implicit val unauthFormat: OFormat[UserNotAuthorised] =
    Json.format[UserNotAuthorised]

  implicit val reads: Reads[ErrorResponse403] = Reads { js =>
    (js \ "code").validate[String].flatMap {
      case "403.2" => js.validate[Forbidden]
      case "403.1" => js.validate[UserNotAuthorised]
      case _       => JsError("Unknown 403 error")
    }
  }

  implicit val writes: Writes[ErrorResponse403] = Writes {
    case f: Forbidden         => Json.toJson(f)
    case u: UserNotAuthorised => Json.toJson(u)
  }

}
