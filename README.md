
# ni-contribution-and-credits-api-stubs

This project provides a stub for the [National Insurance Contribution and Credit service] 

| NINOs to provide | EXPECTED RESPONSE     | STATUS CODE |
|----------------|-----------------------|-------------|
| BB 00 02 00 B  | OK                    | 201         |
| BB 00 04 00 B  | Bad Request           | 400         |
| BB 00 04 01 B  | Unauthorized          | 401         |
| BB 00 04 04 B  | NotFound              | 404         |
| BB 00 04 22 B  | UnprocessableEntity   | 422         |
| Any other NINO | Internal Server Error | 500         |

## Example request

POST request to the following endpoint

```
https://<host>/nps-json-service/nps/v1/api/national-insurance/:nationalInsuranceNumber/contributions-and-credits/from/:startTaxYear/to/:endTaxYear 
```

```json
{
  "dateOfBirth": "1960-04-15"
}
```

### Example 200 Response

```json
{
  "niContribution": [
    {
      "taxYear": 2022,
      "contributionCategoryLetter": "s",
      "contributionCategory": "(NONE)",
      "totalContribution": 99999999999999.98,
      "primaryContribution": 99999999999999.98,
      "class1ContributionStatus": "COMPLIANCE & YIELD INCOMPLETE",
      "primaryPaidEarnings": 99999999999999.98
    }
  ],
  "niCredit": [
    {
      "taxYear": 2022,
      "numberOfCredits": 53,
      "contributionCreditTypeCode": "C2",
      "contributionCreditType": "CLASS 2 - NORMAL RATE",
      "class2Or3EarningsFactor": 99999999999999.98,
      "class2NicAmount": 99999999999999.98,
      "class2Or3CreditStatus": "NOT KNOWN/NOT APPLICABLE"
    }
  ]
}
```

### Example 400 Response

```json
{
  "failures": [
    {
      "reason": "HTTP message not readable",
      "code": ""
    },
    {
      "reason": "Constraint Violation - Invalid/Missing input parameter",
      "code": "BAD_REQUEST" 
    }
  ]
}
```

### License

This code is open source software licensed under the [Apache 2.0 License]("http://www.apache.org/licenses/LICENSE-2.0.html").