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
import play.api.libs.json._
import play.api.mvc.{Action, AnyContent, ControllerComponents}
import uk.gov.hmrc.nicontributionandcreditsapistubs.models._
import uk.gov.hmrc.nicontributionandcreditsapistubs.models.errors.{Failure, Failures, HIPFailure, HIPFailures}
import uk.gov.hmrc.play.bootstrap.backend.controller.BackendController

import java.nio.file.{Files, Paths}
import javax.inject.{Inject, Singleton}
import scala.collection.mutable
import scala.concurrent.Future
import scala.io.Source

@Singleton()
class NIContributionAndCreditController @Inject()(cc: ControllerComponents)
  extends BackendController(cc) with Logging {

  private val builder = Json.newBuilder

  def contributionsAndCredits(nationalInsuranceNumber: String,
                              startTaxYear: Int,
                              endTaxYear: Int): Action[AnyContent] = Action.async { implicit request =>

    nationalInsuranceNumber match {
      case "NY634367C" | "WP103133" =>
        val stream = readJsonFromFile(nationalInsuranceNumber)

        stream match {
          case Some(value) =>
            Future.successful(Ok(value))
          case None =>
            Future.successful(InternalServerError)
        }

      case "JA000017B" | "JA000017" =>
        var responseVariant = ""

        request.body.asJson match {
          case Some(json) =>
            json.validate[NICCRequestPayload] match {
              case JsSuccess(data, _) =>
                if (data.dateOfBirth.toString.equals("1956-10-03")) responseVariant = "_1"
                else responseVariant = "_2"

              case JsError(_) =>
                Future.successful(InternalServerError)
            }

          case _ =>
            Future.successful(InternalServerError)
        }

        val stream = readJsonFromFile(s"JA000017B${responseVariant}")

        stream match {
          case Some(value) =>
            responseVariant match {
              case "_1" =>
                Future.successful(Ok(value))
              case "_2" =>
                Future.successful(NotFound(value))
            }
          case None =>
            Future.successful(InternalServerError)
        }
      case "BE699233A" =>
        val stream = readJsonFromFile(nationalInsuranceNumber)

        stream match {
          case Some(value) =>
            Future.successful(UnprocessableEntity(value))
          case None =>
            Future.successful(InternalServerError)
        }

      case "AA123456C" =>
        val stream = readJsonFromFile(nationalInsuranceNumber)

        stream match {
          case Some(value) =>
            Future.successful(NotFound(value))
          case None =>
            Future.successful(InternalServerError)
        }

      case "AA271213C" =>
        var responseVariant: String = ""

        if (startTaxYear.equals(2024)) responseVariant = "_1"
        else if (endTaxYear - startTaxYear > 6) responseVariant = "_2"

        request.body.asJson match {
          case Some(json) =>
            json.validate[NICCRequestPayload] match {
              case JsSuccess(data, _) =>
                if (data.dateOfBirth.getYear.equals(8888)) responseVariant = "_3"

              case JsError(_) =>
                Future.successful(InternalServerError)
            }

          case _ =>
            Future.successful(InternalServerError)
        }

        val stream = readJsonFromFile(s"${nationalInsuranceNumber}${responseVariant}")

        stream match {
          case Some(value) =>
            responseVariant match {
              case "_3" =>
                Future.successful(BadRequest(value))
              case _ =>
                Future.successful(UnprocessableEntity(value))
            }
          case None =>
            Future.successful(InternalServerError)
        }

      case "BB000200A" =>
        val nIContributionsList = new mutable.ListBuffer[NICCClass1]()
        val nICreditList = new mutable.ListBuffer[NICCClass2]()

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
      case "BB000200B" | "BB000200" =>
        val nIContributionsList = new mutable.ListBuffer[NICCClass1]()
        val nICreditList = new mutable.ListBuffer[NICCClass2]()

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
      case "BB000408A" =>
        Thread.sleep(10000)
        Future.successful(RequestTimeout)
      case "BB000400A" =>

        val origin = "HIP"
        val response = new HIPFailures(
          Seq(HIPFailure("HTTP message not readable", ""), HIPFailure("Constraint Violation - Invalid/Missing input parameter", "BAD_REQUEST"))
        )

        Future.successful(BadRequest(buildHIPErrorResponse(origin, response)))
      case "BB000400B" =>

        val origin = "HoD"
        val response = new Failures(
          Seq(Failure("HTTP message not readable", ""), Failure("Constraint Violation - Invalid/Missing input parameter", "BAD_REQUEST"))
        )

        Future.successful(BadRequest(buildErrorResponse(origin, response)))
      case "BB000401B" =>
        val failuresList = new mutable.ListBuffer[Failure]()
        failuresList += new Failure("Unauthorised", "Invalid Credentials")

        Future.successful(Unauthorized(buildFailuresResponse(failuresList)))
      case "BB000403B" =>
        val failure = Json.toJson(new Failure("Forbidden", "403.2"))

        Future.successful(Unauthorized(failure))
      case "BB000404B" =>
        Future.successful(NotFound)
      case "BB000422B" =>
        val failuresList = new mutable.ListBuffer[Failure]()
        failuresList += new Failure("Start tax year after end tax year", "63496")

        Future.successful(UnprocessableEntity(buildFailuresResponse(failuresList)))
      case "BB000503B" =>

        val origin = "HIP"
        val response = new Failures(
          Seq(Failure("HTTP message not readable", ""), Failure("Constraint Violation - Invalid/Missing input parameter", "BAD_REQUEST"))
        )


        Future.successful(ServiceUnavailable(buildErrorResponse(origin, response)))
      case _ =>
        Future.successful(InternalServerError)
    }
  }

  private def readJsonFromFile(nationalInsuranceNumber: String): Option[JsValue] = {
    val file = Files.exists(Paths.get(s"app/assets/jsons/${nationalInsuranceNumber}.json"))
    file match {
      case true =>
        Option(Source.fromFile(s"app/assets/jsons/${nationalInsuranceNumber}.json").getLines.mkString).map(Json.parse)
      case _ => None
    }
  }

  private def buildSuccessfulResponse(niContributionsList: mutable.ListBuffer[NICCClass1],
                                      nICreditList: Option[mutable.ListBuffer[NICCClass2]]): JsObject = {
    builder.clear()

    builder += ("niClass1" -> niContributionsList)
    builder += ("niClass2" -> nICreditList)

    builder.result()
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

}