package com.gramzin.weather.domain.model

import com.gramzin.weather.domain.PartOfDay
import com.gramzin.weather.domain.WeatherType

data class Weather(
    val time: Long,
    val weatherType: WeatherType,
    val temp: Int,
    val feelsLike: Int,
    val windSpeed: Int,
    val cloudiness: Int,
    val humidity: Int,
    val sunriseTime: Long,
    val sunsetTime: Long
)
