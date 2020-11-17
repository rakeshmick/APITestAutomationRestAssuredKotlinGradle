package weather.utilities

import org.junit.Assert
import weather.data.support.Day
import weather.data.support.WeatherPayloadData
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class Utilities(){
    companion object utility{
        fun assertData(c : Day){
             Assert.assertTrue(isWithinRange(LocalDate.parse(c.date, DateTimeFormatter.ofPattern("yyyy-MM-dd"))))

        }

        fun setPayLoad(postcode: String, country: String, units: String): WeatherPayloadData {
            return WeatherPayloadData(postcode, country, units)
        }

        fun getDay(day: String): String{

            val date = LocalDate.parse(day, DateTimeFormatter.ISO_DATE)
            return date.dayOfWeek.toString()
        }


        fun isWithinRange(testDate: LocalDate): Boolean {
            return !(testDate.isBefore(setStartDate()) || testDate.isAfter(setEndDate()))
        }

        fun setStartDate(): LocalDate? {
            return  java.time.ZonedDateTime.now().toLocalDate()
        }
        fun setEndDate(): LocalDate? {
            val cal = Calendar.getInstance();
            cal.add(Calendar.DAY_OF_YEAR,16)

            return LocalDateTime.ofInstant(cal.toInstant(), cal.getTimeZone().toZoneId()).toLocalDate();
        }
    }
}


