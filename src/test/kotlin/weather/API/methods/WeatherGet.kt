package weather.API.methods

import io.restassured.response.Response
import io.restassured.RestAssured.given
import weather.data.support.WeatherPayloadData


class WeatherGet {
    private val baseUrl: String = weather.conf.DefaultKeys.base_url
    private val key: String= weather.conf.DefaultKeys.api_key

    lateinit var response: Response


    fun getWeatherWithLocation(weatherPayloadData: WeatherPayloadData): Response? {
        response = given().params("postal_code", weatherPayloadData.postcode)
            .params("country", weatherPayloadData.country)
            .params("units", weatherPayloadData.units)
            .params("key", key).`when`()
            .get(baseUrl)

        return response
    }


}