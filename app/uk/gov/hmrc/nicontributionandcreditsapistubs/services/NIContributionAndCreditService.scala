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

import play.api.libs.json.{JsObject, Json}
import play.api.mvc.Result
import play.api.mvc.Results._
import uk.gov.hmrc.nicontributionandcreditsapistubs.models.errors.{Failure, Failures, HIPFailure, HIPFailures}
import uk.gov.hmrc.nicontributionandcreditsapistubs.models.{NICCClass1, NICCClass2, NICCRequestPayload}
import uk.gov.hmrc.nicontributionandcreditsapistubs.utils.JsonUtils

import java.util.Calendar
import javax.inject.Inject
import scala.collection.mutable
import scala.collection.mutable.ListBuffer
import scala.concurrent.Future

class NIContributionAndCreditService @Inject()(jsonUtils: JsonUtils) {
  private val builder = Json.newBuilder

  def statusMapping(nationalInsuranceNumber: String, startTaxYear: Int, endTaxYear: Int, niccRequestPayload: NICCRequestPayload): Future[Result] = {


    val cal = Calendar.getInstance()

    if (startTaxYear >= cal.get(Calendar.YEAR) || endTaxYear >= cal.get(Calendar.YEAR)) Future.successful(UnprocessableEntity(jsonUtils.readJsonFile("conf/resources/data/jsons/AA271213_1.json")))
    else if (endTaxYear - startTaxYear > 6) Future.successful(UnprocessableEntity(jsonUtils.readJsonFile("conf/resources/data/jsons/AA271213_2.json")))
    else if (niccRequestPayload.dateOfBirth.getYear >= cal.get(Calendar.YEAR) - 16) Future.successful(BadRequest(jsonUtils.readJsonFile("conf/resources/data/jsons/AA271213_3.json")))
    else if (startTaxYear < 1975) Future.successful(UnprocessableEntity(jsonUtils.readJsonFile("conf/resources/data/jsons/BE699233.json")))
    else
      nationalInsuranceNumber match {
        case x if x.startsWith("NY634367") =>
          val response = jsonUtils.readJsonFile(s"conf/resources/data/jsons/NY634367.json")

          if (niccRequestPayload.dateOfBirth.toString.equals("1999-01-27")) Future.successful(Ok(response))
          else Future.successful(NotFound(jsonUtils.readJsonFile("conf/resources/data/jsons/NOT_FOUND.json")))

        case x if x.startsWith("WP103133") =>
          val response = jsonUtils.readJsonFile(s"conf/resources/data/jsons/WP103133.json")

          if (niccRequestPayload.dateOfBirth.toString.equals("1970-03-12")) Future.successful(Ok(response))
          else Future.successful(NotFound(jsonUtils.readJsonFile("conf/resources/data/jsons/NOT_FOUND.json")))

        case x if x.startsWith("JA000017") =>

          if (niccRequestPayload.dateOfBirth.toString.equals("1956-10-03")) Future.successful(Ok(jsonUtils.readJsonFile(s"conf/resources/data/jsons/JA000017.json")))
          else Future.successful(NotFound(jsonUtils.readJsonFile("conf/resources/data/jsons/NOT_FOUND.json")))

        case x if x.startsWith("AA271213") =>
          if (niccRequestPayload.dateOfBirth.toString.equals("1969-12-09")) Future.successful(InternalServerError)
          else Future.successful(NotFound(jsonUtils.readJsonFile("conf/resources/data/jsons/NOT_FOUND.json")))

        case "BB000200A" =>
          val nIContributionsList = new ListBuffer[NICCClass1]()
          val nICreditList = new ListBuffer[NICCClass2]()

          nIContributionsList += new NICCClass1(2022,
            "s",
            "(NONE)",
            "C1",
            99999999999999.98,
            "COMPLIANCE & YIELD INCOMPLETE",
            99999999999999.98)

          nICreditList += new NICCClass2(2022,
            53,
            "C2",
            99999999999999.98,
            99999999999999.98,
            "NOT KNOWN/NOT APPLICABLE")
          val jsonResponse = Json.parse("" +
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
            "}")

          Future.successful(Ok(jsonResponse))
        case x if x.startsWith("BB000200") =>
          val nIContributionsList = new ListBuffer[NICCClass1]()
          val nICreditList = new ListBuffer[NICCClass2]()

          nIContributionsList += new NICCClass1(2022,
            "s",
            "(NONE)",
            "C1",
            99999999999999.98,
            "COMPLIANCE & YIELD INCOMPLETE",
            99999999999999.98)

          nICreditList += new NICCClass2(2022,
            53,
            "C2",
            99999999999999.98,
            99999999999999.98,
            "NOT KNOWN/NOT APPLICABLE")

          Future.successful(Ok(buildSuccessfulResponse(nIContributionsList, Some(nICreditList))))
        case x if x.startsWith("BB000408") =>
          Thread.sleep(10000)
          Future.successful(RequestTimeout)
        case x if x.startsWith("BB000400") =>

          val origin = "HIP"
          val response = new HIPFailures(
            Seq(HIPFailure("HTTP message not readable", ""), HIPFailure("Constraint Violation - Invalid/Missing input parameter", "BAD_REQUEST"))
          )

          Future.successful(BadRequest(buildHIPErrorResponse(origin, response)))
        case x if x.startsWith("BB000400") =>

          val origin = "HoD"
          val response = new Failures(
            Seq(Failure("HTTP message not readable", ""), Failure("Constraint Violation - Invalid/Missing input parameter", "BAD_REQUEST"))
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
            Seq(Failure("HTTP message not readable", ""), Failure("Constraint Violation - Invalid/Missing input parameter", "BAD_REQUEST"))
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
    builder += ("origin" -> origin)
    builder += ("response" -> failuresResponse)
    builder.result()
  }

  private def buildErrorResponse(origin: String, failuresResponse: Failures): JsObject = {
    builder.clear()
    builder += ("origin" -> origin)
    builder += ("response" -> failuresResponse)
    builder.result()
  }

  private def buildSuccessfulResponse(niContributionsList: mutable.ListBuffer[NICCClass1],
                                      nICreditList: Option[mutable.ListBuffer[NICCClass2]]): JsObject = {
    builder.clear()

    builder += ("niClass1" -> niContributionsList)
    builder += ("niClass2" -> nICreditList)

    builder.result()
  }

}