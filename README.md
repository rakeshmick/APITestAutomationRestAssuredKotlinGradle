# WeatherTestWooCuckRest


sample report already ran

https://reports.cucumber.io/reports/ba6cfda9-9fb9-4f9b-9777-121c681884d4

command to run 
./gradlew cucumber  

* This a kotlin cucumber project.
* once test is run, you can see the result report link in the response and please
open it online to see the result.

* I have used Kotlin, Cucumber and RestAssured to test the API.
* API key is hardcoded in the code for as an object for now, which can be kept in
and environment variable or something for security .

Changes made based on recommendations:
* Changed the project structure with proper package names, class name, function name etc
* made some changes in the overall structure
* added Background to BDD scenario, which sets config details
* removed hard coding of expected conditions
* added test to verify the resulting days are in next 16 days as advised in the discussion
* added a new utility class which contains utility function its using companion object
* added new logic to verify the resulting days are within the 16 days
* changed scenarios to pass postcode via the right scenario step
* logic to filter the dates based on temperature has been revisited
* removed log of response body from the html result.