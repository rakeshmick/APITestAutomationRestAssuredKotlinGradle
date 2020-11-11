@functional
Feature: Surfer

  Scenario Outline: As a choosy surfer


    Given I like to surf in any beach with postcode <postcode> of Sydney in <country>
    And I only like to surf on any two days specifically <Day> in next 16 Days
    When I look up the the weather forecast for the next 16 days using POSTAL CODES
    Then I check to if see the temperature is between <temp_range>
    And I Pick days based on suitable weather forecast for the day
    Then i check if the data is right <result>

    Examples:
      | postcode |Day|country     | temp_range | result|
      |  2150    |"THURSDAY"| "AU"       |60 and 90   | "2020-11-19"|
      |  2150    |"WEDNESDAY"| "AU"       |60 and 90   | "2020-11-11,2020-11-25"|

