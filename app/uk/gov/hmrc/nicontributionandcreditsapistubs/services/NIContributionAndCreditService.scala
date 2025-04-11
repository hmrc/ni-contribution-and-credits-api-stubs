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

package uk.gov.hmrc.nicontributionandcreditsapistubs.services

import play.api.libs.json.{JsError, JsObject, JsSuccess, Json}
import play.api.mvc.Result
import play.api.mvc.Results._
import uk.gov.hmrc.nicontributionandcreditsapistubs.models.errors.{Failure, Failures, HIPFailure, HIPFailures}
import uk.gov.hmrc.nicontributionandcreditsapistubs.models.{NICCClass1, NICCClass2, NICCRequestPayload, NPSResponse}
import uk.gov.hmrc.nicontributionandcreditsapistubs.utils.JsonUtils

import java.util.Calendar
import javax.inject.Inject
import scala.collection.mutable
import scala.collection.mutable.ListBuffer
import scala.concurrent.Future

class NIContributionAndCreditService @Inject() (jsonUtils: JsonUtils) {
  private val builder = Json.newBuilder

  def statusMapping(
      nationalInsuranceNumber: String,
      startTaxYear: Int,
      endTaxYear: Int,
      niccRequestPayload: NICCRequestPayload
  ): Future[Result] = {

    val cal = Calendar.getInstance()

    if (startTaxYear >= cal.get(Calendar.YEAR))
      Future.successful(UnprocessableEntity(jsonUtils.readJsonFile("conf/resources/data/jsons/AA271213_1.json")))
    else if (endTaxYear >= cal.get(Calendar.YEAR))
      Future.successful(
        UnprocessableEntity(jsonUtils.readJsonFile("conf/resources/data/jsons/END_TAX_YEAR_AFTER_CY-1.json"))
      )
    else if (startTaxYear > endTaxYear)
      Future.successful(
        UnprocessableEntity(jsonUtils.readJsonFile("conf/resources/data/jsons/START_YEAR_AFTER_END_YEAR.json"))
      )
    else if (endTaxYear - startTaxYear >= 6)
      Future.successful(UnprocessableEntity(jsonUtils.readJsonFile("conf/resources/data/jsons/AA271213_2.json")))
    else if (niccRequestPayload.dateOfBirth.getYear >= cal.get(Calendar.YEAR) - 16)
      Future.successful(BadRequest(jsonUtils.readJsonFile("conf/resources/data/jsons/AA271213_3.json")))
    else if (startTaxYear < 1975)
      Future.successful(UnprocessableEntity(jsonUtils.readJsonFile("conf/resources/data/jsons/BE699233.json")))
    else
      nationalInsuranceNumber match {
        case x if x.startsWith("TX000200A") =>
          val response = jsonUtils.readJsonFile("conf/resources/data/jsons/TX000200A.json")

          if (niccRequestPayload.dateOfBirth.toString.equals("1999-01-27")) Future.successful(Ok(response))
          else Future.successful(NotFound(jsonUtils.readJsonFile("conf/resources/data/jsons/NOT_FOUND.json")))

        case x if x.startsWith("TX000200B") =>
          val response = jsonUtils.readJsonFile("conf/resources/data/jsons/TX000200B.json")

          if (niccRequestPayload.dateOfBirth.toString.equals("1999-01-27")) Future.successful(Ok(response))
          else Future.successful(NotFound(jsonUtils.readJsonFile("conf/resources/data/jsons/NOT_FOUND.json")))

        case x if x.startsWith("TX000200C") =>
          val response = jsonUtils.readJsonFile("conf/resources/data/jsons/TX000200C.json")

          if (niccRequestPayload.dateOfBirth.toString.equals("1999-01-27")) Future.successful(Ok(response))
          else Future.successful(NotFound(jsonUtils.readJsonFile("conf/resources/data/jsons/NOT_FOUND.json")))

        case x if x.startsWith("TX000200D") =>
          val response = jsonUtils.readJsonFile("conf/resources/data/jsons/TX000200D.json")

          if (niccRequestPayload.dateOfBirth.toString.equals("1999-01-27")) Future.successful(Ok(response))
          else Future.successful(NotFound(jsonUtils.readJsonFile("conf/resources/data/jsons/NOT_FOUND.json")))

        case x if x.startsWith("TR000200A") =>
          val response = jsonUtils.readJsonFile("conf/resources/data/jsons/TR000200A.json")

          if (niccRequestPayload.dateOfBirth.toString.equals("1999-01-27")) Future.successful(Ok(response))
          else Future.successful(NotFound(jsonUtils.readJsonFile("conf/resources/data/jsons/NOT_FOUND.json")))

        case x if x.startsWith("NY634367") =>
          val response = jsonUtils.readJsonFile("conf/resources/data/jsons/NY634367.json")

          if (niccRequestPayload.dateOfBirth.toString.equals("1999-01-27")) Future.successful(Ok(response))
          else Future.successful(NotFound(jsonUtils.readJsonFile("conf/resources/data/jsons/NOT_FOUND.json")))

        case x if x.startsWith("WP103133") =>
          val response = jsonUtils.readJsonFile("conf/resources/data/jsons/WP103133.json")

          if (niccRequestPayload.dateOfBirth.toString.equals("1970-03-12")) Future.successful(Ok(response))
          else Future.successful(NotFound(jsonUtils.readJsonFile("conf/resources/data/jsons/NOT_FOUND.json")))

        case x if x.startsWith("JA000017") =>

          if (niccRequestPayload.dateOfBirth.toString.equals("1956-10-03"))
            Future.successful(Ok(jsonUtils.readJsonFile("conf/resources/data/jsons/JA000017.json")))
          else Future.successful(NotFound(jsonUtils.readJsonFile("conf/resources/data/jsons/NOT_FOUND.json")))

        case x if x.startsWith("AA271213") =>
          if (niccRequestPayload.dateOfBirth.toString.equals("1969-12-09")) Future.successful(InternalServerError)
          else Future.successful(NotFound(jsonUtils.readJsonFile("conf/resources/data/jsons/NOT_FOUND.json")))

        case x if x.startsWith("RN001856") =>
          if (niccRequestPayload.dateOfBirth.toString.equals("1958-01-19"))
            Future.successful(Ok(jsonUtils.readJsonFile("conf/resources/data/jsons/RN001856.json")))
          else Future.successful(NotFound(jsonUtils.readJsonFile("conf/resources/data/jsons/NOT_FOUND.json")))

        case x if x.startsWith("RN001857") =>
          if (niccRequestPayload.dateOfBirth.toString.equals("1980-04-18"))
            Future.successful(Ok(jsonUtils.readJsonFile("conf/resources/data/jsons/RN001857.json")))
          else Future.successful(NotFound(jsonUtils.readJsonFile("conf/resources/data/jsons/NOT_FOUND.json")))

        case x if x.startsWith("RN001859") =>
          if (niccRequestPayload.dateOfBirth.toString.equals("1967-07-22"))
            Future.successful(Ok(jsonUtils.readJsonFile("conf/resources/data/jsons/RN001859.json")))
          else Future.successful(NotFound(jsonUtils.readJsonFile("conf/resources/data/jsons/NOT_FOUND.json")))

        case x if x.startsWith("RN001965") =>
          if (niccRequestPayload.dateOfBirth.toString.equals("1958-12-25"))
            Future.successful(Ok(jsonUtils.readJsonFile("conf/resources/data/jsons/RN001965.json")))
          else Future.successful(NotFound(jsonUtils.readJsonFile("conf/resources/data/jsons/NOT_FOUND.json")))

        case x if x.startsWith("RN001966") =>
          if (niccRequestPayload.dateOfBirth.toString.equals("1970-02-16"))
            Future.successful(Ok(jsonUtils.readJsonFile("conf/resources/data/jsons/RN001966.json")))
          else Future.successful(NotFound(jsonUtils.readJsonFile("conf/resources/data/jsons/NOT_FOUND.json")))

        case x if x.startsWith("RN001967") =>

          if (niccRequestPayload.dateOfBirth.toString.equals("1985-10-19")) {
            val jsonResponse = jsonUtils.readJsonFile(s"conf/resources/data/jsons/RN001967.json")
            val npsResponse: Option[NPSResponse] = jsonResponse.validate[NPSResponse] match {
              case JsSuccess(data, _) => Some(data)
              //
              case JsError(_) => None
            }

            npsResponse match {
              case Some(npsResponse) =>
                val requestedClass1: Option[Seq[NICCClass1]] = npsResponse.niClass1 match {
                  case Some(data) =>
                    Option(data.filter(niClass1 => niClass1.taxYear >= startTaxYear && niClass1.taxYear <= endTaxYear))
                      .filter(_.nonEmpty)

                  case _ => None
                }
                val requestedClass2: Option[Seq[NICCClass2]] = npsResponse.niClass2 match {
                  case Some(data) =>
                    Option(data.filter(niClass2 => niClass2.taxYear >= startTaxYear && niClass2.taxYear <= endTaxYear))
                      .filter(_.nonEmpty)
                  case _ => None
                }

                Future.successful(Ok(Json.toJson(new NPSResponse(requestedClass1, requestedClass2))))

              case _ => Future.successful(InternalServerError)
            }
          } else Future.successful(NotFound(jsonUtils.readJsonFile("conf/resources/data/jsons/NOT_FOUND.json")))

        case x if x.startsWith("RN001969") =>
          if (niccRequestPayload.dateOfBirth.toString.equals("1951-07-07"))
            Future.successful(Ok(jsonUtils.readJsonFile(s"conf/resources/data/jsons/RN001969.json")))
          else Future.successful(NotFound(jsonUtils.readJsonFile("conf/resources/data/jsons/NOT_FOUND.json")))

        case x if x.startsWith("RN001970") =>
          if (niccRequestPayload.dateOfBirth.toString.equals("1989-07-08"))
            Future.successful(Ok(jsonUtils.readJsonFile(s"conf/resources/data/jsons/RN001970.json")))
          else Future.successful(NotFound(jsonUtils.readJsonFile("conf/resources/data/jsons/NOT_FOUND.json")))

        case x if x.startsWith("RN001973") =>
          if (niccRequestPayload.dateOfBirth.toString.equals("1951-07-07"))
            Future.successful(Ok(jsonUtils.readJsonFile(s"conf/resources/data/jsons/RN001973.json")))
          else Future.successful(NotFound(jsonUtils.readJsonFile("conf/resources/data/jsons/NOT_FOUND.json")))

        case "BB000200A" =>
          val nIContributionsList = new ListBuffer[NICCClass1]()
          val nICreditList        = new ListBuffer[NICCClass2]()

          nIContributionsList += new NICCClass1(
            2022,
            "s",
            "(NONE)",
            "C1",
            99999999999999.98,
            "COMPLIANCE & YIELD INCOMPLETE",
            99999999999999.98
          )

          nICreditList += new NICCClass2(
            2022,
            53,
            "C2",
            99999999999999.98,
            99999999999999.98,
            "NOT KNOWN/NOT APPLICABLE"
          )
          val jsonResponse = Json.parse(
            "" +
              "{" +
              "  \"niClass1\": [" +
              "    {" +
              "      \"taxYear\": 2018," +
              "      \"contributionCategoryLetter\": \"A\"," +
              "      \"contributionCategory\": \"STANDARD RATE\"," +
              "      \"primaryContribution\": 3189.12," +
              "      \"class1ContributionStatus\": \"VALID\"," +
              "      \"primaryPaidEarnings\": 35000," +
              "      \"contributionCreditType\": \"EON\"" +
              "    }," +
              "    {" +
              "      \"taxYear\": 2019," +
              "      \"contributionCategoryLetter\": \"A\"," +
              "      \"contributionCategory\": \"STANDARD RATE\"," +
              "      \"primaryContribution\": 1964.16," +
              "      \"class1ContributionStatus\": \"VALID\"," +
              "      \"primaryPaidEarnings\": 25000," +
              "      \"contributionCreditType\": \"EON\"" +
              "    }," +
              "    {" +
              "      \"taxYear\": 2020," +
              "      \"contributionCategoryLetter\": \"A\"," +
              "      \"contributionCategory\": \"STANDARD RATE\"," +
              "      \"primaryContribution\": 1964.16," +
              "      \"class1ContributionStatus\": \"VALID\"," +
              "      \"primaryPaidEarnings\": 25000," +
              "      \"contributionCreditType\": \"C1\"" +
              "    }" +
              "  ]" +
              "}"
          )

          Future.successful(Ok(jsonResponse))

        case x if x.startsWith("BB000200") =>
          val nIContributionsList = new ListBuffer[NICCClass1]()
          val nICreditList        = new ListBuffer[NICCClass2]()

          nIContributionsList += new NICCClass1(
            2022,
            "s",
            "(NONE)",
            "C1",
            99999999999999.98,
            "COMPLIANCE & YIELD INCOMPLETE",
            99999999999999.98
          )

          nICreditList += new NICCClass2(
            2022,
            53,
            "C2",
            99999999999999.98,
            99999999999999.98,
            "NOT KNOWN/NOT APPLICABLE"
          )

          Future.successful(Ok(buildSuccessfulResponse(nIContributionsList, Some(nICreditList))))

        case x if x.startsWith("BB000408") =>
          Thread.sleep(10000)
          Future.successful(RequestTimeout)

        case x if x.startsWith("BB000400") =>

          val origin = "HIP"
          val response = new HIPFailures(
            Seq(
              HIPFailure("HTTP message not readable", ""),
              HIPFailure("Constraint Violation - Invalid/Missing input parameter", "BAD_REQUEST")
            )
          )

          Future.successful(BadRequest(buildHIPErrorResponse(origin, response)))

        case x if x.startsWith("BB000400") =>

          val origin = "HoD"
          val response = new Failures(
            Seq(
              Failure("HTTP message not readable", ""),
              Failure("Constraint Violation - Invalid/Missing input parameter", "BAD_REQUEST")
            )
          )

          Future.successful(BadRequest(buildErrorResponse(origin, response)))

        case x if x.startsWith("BB000401") =>
          val failuresList = new ListBuffer[Failure]()
          failuresList += new Failure("Unauthorised", "Invalid Credentials")

          Future.successful(Unauthorized(buildFailuresResponse(failuresList)))

        case x if x.startsWith("BB000403") =>
          val failure = Json.toJson(new Failure("Forbidden", "403.2"))

          Future.successful(Unauthorized(failure))

        case x if x.startsWith("BB000404") =>
          Future.successful(NotFound(jsonUtils.readJsonFile("conf/resources/data/jsons/NOT_FOUND.json")))

        case x if x.startsWith("BB000422") =>
          val failuresList = new ListBuffer[Failure]()
          failuresList += new Failure("Start tax year after end tax year", "63496")

          Future.successful(UnprocessableEntity(buildFailuresResponse(failuresList)))

        case x if x.startsWith("BB000503") =>

          val origin = "HIP"
          val response = new Failures(
            Seq(
              Failure("HTTP message not readable", ""),
              Failure("Constraint Violation - Invalid/Missing input parameter", "BAD_REQUEST")
            )
          )

          Future.successful(ServiceUnavailable(buildErrorResponse(origin, response)))

        case _ =>
          Future.successful(NotFound(jsonUtils.readJsonFile("conf/resources/data/jsons/NOT_FOUND.json")))
      }
  }

  private def buildFailuresResponse(failuresList: mutable.ListBuffer[Failure]): JsObject = {
    builder.clear()
    builder += ("failures" -> failuresList)
    builder.result()
  }

  private def buildHIPErrorResponse(origin: String, failuresResponse: HIPFailures): JsObject = {
    builder.clear()
    builder += ("origin"   -> origin)
    builder += ("response" -> failuresResponse)
    builder.result()
  }

  private def buildErrorResponse(origin: String, failuresResponse: Failures): JsObject = {
    builder.clear()
    builder += ("origin"   -> origin)
    builder += ("response" -> failuresResponse)
    builder.result()
  }

  private def buildSuccessfulResponse(
      niContributionsList: mutable.ListBuffer[NICCClass1],
      nICreditList: Option[mutable.ListBuffer[NICCClass2]]
  ): JsObject = {
    builder.clear()

    builder += ("niClass1" -> niContributionsList)
    builder += ("niClass2" -> nICreditList)

    builder.result()
  }

}
