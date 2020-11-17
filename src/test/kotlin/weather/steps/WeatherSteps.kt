package weather.steps

import io.cucumber.java.Before
import io.cucumber.java.Scenario
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import io.restassured.response.Response
import org.junit.Assert
import weather.API.methods.WeatherGet
import weather.data.support.Day
import weather.data.support.WeatherPayloadData
import weather.utilities.Utilities
import kotlin.collections.ArrayList


class WeatherSteps {
    private var scenario: Scenario? = null
    lateinit var response: Response
    lateinit var postcode: String
    lateinit var country: String
    var datesToSelect = ArrayList<String>()
    lateinit var weatherPayloadData: WeatherPayloadData
    var expectedLow: Float = 0.0F
    var expectedHigh: Float = 0.0F
    val days = ArrayList<Day>()
    var daysFilteredPerRequirements = ArrayList<Day>()
    private var weatherGet: WeatherGet?= null

    @Before
    fun before(scen: Scenario?) {
        scenario = scen
    }


    @Given("I do all basic set up")
    fun i_do_all_basic_setup(){
        weatherGet = WeatherGet()
    }

    @Given("I like to surf in any beach with postcode of Sydney in {string}")
    fun i_like_to_surf_in_any_two_beaches_of_sydney(countryCode: String) {

        country = countryCode
    }


    @Given("I only like to surf on any particular day specifically {string} in next 16 Days")
    fun i_only_like_to_surf_on_any_days_specifically_in_next_days(string: String) {
        datesToSelect.add(string)

    }

    @When("I look up the the weather forecast for the next 16 days using {int}")
    fun i_look_up_the_the_weather_forecast_for_the_next_days_using_postal_codes(postcodes: Int?) {
        postcode = postcodes.toString()
        weatherPayloadData=Utilities.setPayLoad(postcode, country, "I")
        response = weatherGet?.getWeatherWithLocation(weatherPayloadData)!!

       // scenario?.log(response.prettyPrint())
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
                        val dayOfWeekThis = Utilities.getDay(date)
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
        daysFilteredPerRequirements= days.filter { day -> day.day==datesToSelect[0].toString()  } as ArrayList<Day>
        println(days.size)
        for (d in daysFilteredPerRequirements){
            scenario?.log(d.date)
            scenario?.log(d.day)
        }
    }

    @Then("i check if the data is right {string}")
    fun i_check_if_the_data_is_right(string: String) {
        val range = days
        val actual = ArrayList<String>()
        daysFilteredPerRequirements.forEach { c-> actual.add(c.date)
        }
        Assert.assertEquals(range, actual)
    }


    @Then("i verify if the results are within next {int} days")
    fun then_i_verify_the_result(days: Int) {

        val actual = ArrayList<String>()
        for (c in daysFilteredPerRequirements){
            Utilities.assertData(c)
        }
        if (daysFilteredPerRequirements.size<1){
            scenario?.log("For required day "+datesToSelect[0].toString()+" there is no values in result")
        }
    }


}