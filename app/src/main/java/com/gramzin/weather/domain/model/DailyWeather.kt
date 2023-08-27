package com.gramzin.weather.domain.model

import com.gramzin.weather.domain.WeatherType

data class DailyWeather(
    val weatherType: WeatherType,
    val minTemp: Int,
    val maxTemp: Int,
)
