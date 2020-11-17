@functional
Feature: Surfer surfs on a good weather


  Background: Set basic details for every call
      Given I do all basic set up

  Scenario Outline: As a choosy surfer


    Given I like to surf in any beach with postcode of Sydney in <country>
    And I only like to surf on any particular day specifically <Day> in next 16 Days
    When I look up the the weather forecast for the next 16 days using <postcode>
    Then I check to if see the temperature is between <temp_range>
    And I Pick days based on suitable weather forecast for the day
    Then i verify if the results are within next 16 days

    Examples:
      | postcode |Day        |country     | temp_range |
      |  2150    |"THURSDAY" | "AU"       |60 and 90   |
      |  2150    |"WEDNESDAY"| "AU"       |60 and 90   |













    #Given I like to surf in any beach with postcode of Sydney in <country>
    #And I only like to surf on any particular day specifically <Day> in next 16 Days
    #When I look up the the weather forecast for the next 16 days using <postcode>
    #Then I check to if see the temperature is between <temp_range>
    #And I Pick days based on suitable weather forecast for the day
  #  Then i check if the data is right <result>
    #Then i verify if the results are within next 16 days


  #  Examples:
   #   | postcode |Day        |country     | temp_range | result|
    #  |  2150    |"THURSDAY" | "AU"       |60 and 90   | "2020-11-19"|
     # |  2150    |"WEDNESDAY"| "AU"       |60 and 90   | "2020-11-11,2020-11-25"|

