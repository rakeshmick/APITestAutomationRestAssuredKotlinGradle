package io.wool.test.weather

import io.restassured.response.Response
import io.restassured.RestAssured.given


class CurrentWeather {
    private val base_url: String = DefaultKeys.base_url

    lateinit var response: Response


    fun getWeatherWithLocation(weatherPayload: WeatherPayload): Response? {
        response = given().params("postal_code", weatherPayload.postcode)
            .params("country", weatherPayload.country)
            .params("units", weatherPayload.units)
            .params("key", weatherPayload.key).`when`()
            .get(base_url)
        return response
    }


}