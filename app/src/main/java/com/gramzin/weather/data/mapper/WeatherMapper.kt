package com.gramzin.weather.data.mapper

import com.gramzin.weather.data.api.weather.model.WeatherItem
import com.gramzin.weather.domain.WeatherType
import com.gramzin.weather.domain.model.DailyWeather
import com.gramzin.weather.domain.model.Weather
import java.util.Calendar

class WeatherMapper {

    fun mapWeatherToDomain(weatherItem: WeatherItem): Weather {
        val weatherType = mapWeatherTypeToDomain(weatherItem.weather[0].icon)
        return Weather(
            weatherType = weatherType,
            temp = weatherItem.main.temp.toInt(),
            feelsLike = weatherItem.main.feelsLike.toInt(),
            windSpeed = weatherItem.wind.speed.toInt(),
            cloudiness = weatherItem.clouds.all,
            humidity = weatherItem.main.humidity,
            sunriseTime = weatherItem.sys.sunrise,
            sunsetTime = weatherItem.sys.sunset
        )
    }

    fun mapDailyWeatherToDomain(weatherItems: List<WeatherItem>): List<DailyWeather> {
        val dailyWeather = mutableListOf<DailyWeather>()
        var lastDay = weatherItems.getOrNull(0) ?: return emptyList()
        var lastDate = Calendar.getInstance()
        lastDate.timeInMillis = lastDay.dateTime + lastDay.timezone
        var minTemp = lastDay.main.temp
        var maxTemp = lastDay.main.temp
        val weatherCodes = mutableMapOf<String, Int>()

        weatherItems.forEach {
            val currentDate = Calendar.getInstance()
            currentDate.timeInMillis = it.dateTime + it.timezone

            if (lastDate.get(Calendar.DAY_OF_YEAR) == currentDate.get(Calendar.DAY_OF_YEAR)) {
                if (it.main.temp < minTemp) minTemp = it.main.temp
                if (it.main.temp < maxTemp) maxTemp = it.main.temp
                val weatherCode = it.weather[0].icon
                weatherCodes[weatherCode]?.plus(1)
                weatherCodes[weatherCode] ?: weatherCodes.put(weatherCode, 1)
            } else {
                val weatherCode = weatherCodes.maxBy { it.value }.key
                val weatherType = mapWeatherTypeToDomain(weatherCode)
                val weather = DailyWeather(
                    weatherType = weatherType,
                    minTemp = minTemp.toInt(),
                    maxTemp = maxTemp.toInt(),
                    sunriseTime = lastDay.sys.sunrise,
                    sunsetTime = lastDay.sys.sunset
                )
                dailyWeather.add(weather)

                lastDay = it
                lastDate = Calendar.getInstance()
                lastDate.timeInMillis = lastDay.dateTime + lastDay.timezone
                minTemp = lastDay.main.temp
                maxTemp = lastDay.main.temp
            }
        }
        return dailyWeather
    }


    private fun mapWeatherTypeToDomain(weatherCode: String): WeatherType{
        return when(weatherCode.substring(0, 2)){
            "01" -> WeatherType.ClearSky
            "02" -> WeatherType.FewClouds
            "03" -> WeatherType.ScatteredClouds
            "04" -> WeatherType.BrokenClouds
            "09" -> WeatherType.ShowerRain
            "10" -> WeatherType.Rain
            "11" -> WeatherType.ThunderStorm
            "13" -> WeatherType.Snow
            "50" -> WeatherType.Mist
            else -> WeatherType.ClearSky
        }
    }
}