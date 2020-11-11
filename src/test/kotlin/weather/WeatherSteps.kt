package io.wool.test.weather

import io.cucumber.java.Before
import io.cucumber.java.PendingException
import io.cucumber.java.Scenario
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import io.restassured.response.Response
import org.junit.Assert
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class WeatherSteps {
    private var scenario: Scenario? = null
    lateinit var response: Response
    lateinit var postcode: String
    lateinit var country: String
    var datesToSelect = ArrayList<String>()
    lateinit var weatherPayload: WeatherPayload
    var expectedLow: Float = 0.0F
    var expectedHigh: Float = 0.0F
    private val api_key: String = DefaultKeys.api_key
    val days = ArrayList<Day>()
    var correctDays = ArrayList<Day>()

    @Before
    fun before(scen: Scenario?) {
        scenario = scen
    }

    @Given("I like to surf in any beach with postcode {int} of Sydney in {string}")
    fun i_like_to_surf_in_any_two_beaches_of_sydney(postcodes: Int?, countryCode: String) {
        postcode = postcodes.toString()
        country = countryCode
    }


    @Given("I only like to surf on any two days specifically {string} in next 16 Days")

    fun i_only_like_to_surf_on_any_days_specifically_thursday_friday_in_next_days( string:String) {
        datesToSelect.add(string)

    }

    @When("I look up the the weather forecast for the next 16 days using POSTAL CODES")
    fun i_look_up_the_the_weather_forecast_for_the_next_days_using_postal_codes() {
        setPayLoad(postcode, country, "I", api_key)
        response = CurrentWeather().getWeatherWithLocation(weatherPayload)!!

        scenario?.log(response.prettyPrint())
    }

    @Then("I check to if see the temperature is between {int} and {int}")
    fun i_check_to_if_see_the_temperature_is_between_and(int1: Int, int2: Int) {

        expectedLow = int1.toFloat()
        expectedHigh = int2.toFloat()
    }

    @Then("I Pick days based on suitable weather forecast for the day")
    fun i_pick_two_spots_based_on_suitable_weather_forecast_for_the_day() {


        val username: String = response.jsonPath().get("data[0].weather.description")

        println(username)

        var lowTmp: kotlin.collections.List<Float> = response.jsonPath().getList("data.low_temp")
        var maxTmp: kotlin.collections.List<Float> = response.jsonPath().getList("data.max_temp")



        lowTmp.forEachIndexed() { i, ele ->


                    if (ele > expectedLow.toFloat() && maxTmp[i] < expectedHigh.toFloat()) {

                        val date =  response.jsonPath().getString("data[" + i + "].valid_date")
                        val dayOfWeekThis = getDay(date)
                        days.add(
                            Day(
                                ele.toString(),
                                maxTmp[i].toString(),
                                response.jsonPath().getString("data[" + i + "]"),
                                       date,
                                dayOfWeekThis.toString()

                            )
                        )
                    }



        }



        correctDays= days.filter { day -> day.day==datesToSelect[0].toString()  } as ArrayList<Day>

        println(days.size)
        for (d in correctDays){
            scenario?.log(d.date)
            scenario?.log(d.day)
        }


    }

    @Then("i check if the data is right {string}")
    fun i_check_if_the_data_is_right(string:String) {
        val range =string.split(",")
        val actual = ArrayList<String>()
        correctDays.forEach {
            c-> actual.add(c.date)
        }
        Assert.assertEquals(range,actual)
    }


    fun setPayLoad(postcode: String, country: String, units: String, key: String) {
        weatherPayload = WeatherPayload(postcode, country, units, key)
    }





    fun getDay(day: String): String{

        val date = LocalDate.parse(day, DateTimeFormatter.ISO_DATE)
        return date.dayOfWeek.toString()
    }


}