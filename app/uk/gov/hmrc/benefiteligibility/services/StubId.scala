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

/** Represents all stub identifiers for benefit eligibility.
  *
  * @note
  *   This enum uses [[enumeratum.Enum]] and [[enumeratum.EnumEntry]].
  */
sealed trait StubId extends EnumEntry

object StubId extends Enum[StubId] {

  /** The sequence of all StubId values */
  val values: immutable.IndexedSeq[StubId] = findValues

  case object AA000001A                             extends StubId
  case object AA000002A                             extends StubId
  case object AA000002                              extends StubId
  case object AA000003                              extends StubId
  case object AA000004                              extends StubId
  case object AA000005                              extends StubId
  case object AA000006                              extends StubId
  case object AA000007                              extends StubId
  case object AA000008                              extends StubId
  case object AB123456C                             extends StubId
  case object PaginationCompleteForMarriageDetails  extends StubId
  case object PaginationCompleteForLiabilityDetails extends StubId
  case object GK938415                              extends StubId
  case object S2123456B                             extends StubId
  case object S2345678C                             extends StubId
  case object AA000009                              extends StubId
  case object RN001296B                             extends StubId
  case object RN001291A                             extends StubId
  case object RN001308B                             extends StubId
  case object RN001289C                             extends StubId
  case object RN001288B                             extends StubId
  case object RN001287A                             extends StubId
  case object RN001286D                             extends StubId
  case object S8999000N                             extends StubId
  case object A7123456Q                             extends StubId
  case object CK000021B                             extends StubId
  case object CK000003B                             extends StubId
  case object CE002370A                             extends StubId
  case object AA271213C                             extends StubId
  case object CK000008B                             extends StubId
  case object NY634367C                             extends StubId
  case object BE699233A                             extends StubId
  case object JG796219A                             extends StubId
  case object JA000017B                             extends StubId
  case object PW899033A                             extends StubId
  case object PW908233A                             extends StubId
  case object ZC249813B                             extends StubId
  case object RN001856A                             extends StubId
  case object RN001857B                             extends StubId
  case object RN001859D                             extends StubId
  case object RN001965B                             extends StubId
  case object RN001966C                             extends StubId
  case object RN001969B                             extends StubId
  case object RN001970C                             extends StubId
  case object RN001973B                             extends StubId
  case object RN001967D                             extends StubId
  case object RN001968A                             extends StubId
  case object RN001277C                             extends StubId
  case object RN001281C                             extends StubId
  case object RN001280B                             extends StubId
  case object RN001284B                             extends StubId
  case object RN001295A                             extends StubId
  case object RN001294D                             extends StubId
  case object RN001282D                             extends StubId
  case object RN001285C                             extends StubId
  case object RN001293C                             extends StubId
  case object RN001292B                             extends StubId
}
