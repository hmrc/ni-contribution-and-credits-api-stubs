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

import enumeratum.{Enum, EnumEntry}
import scala.collection.immutable

sealed trait StubId extends EnumEntry

object StubId extends Enum[StubId] {
  val values: immutable.IndexedSeq[StubId] = findValues
  case object AA000001A extends StubId
  case object AA000002A extends StubId
  case object AA000002  extends StubId
  case object AA000003  extends StubId
  case object AA000004  extends StubId
  case object AA000005  extends StubId
  case object AA000006  extends StubId
}
