Feature: Get API info
	Scenario Outline: User calls web service to get API info
		Given a user retrieves the API
		When a user with id "<Id>"
		Then the status code is <StatusCode>

		Examples:
			|Id|StatusCode|
			| 2|200       |
