package com.gramzin.weather.data.api.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WeatherItem(
    @Json(name = "clouds")
    val clouds: Clouds,
    @Json(name = "dt")
    val dateTime: Long,
    @Json(name = "main")
    val main: Main,
    @Json(name = "sys")
    val sys: Sys,
    @Json(name = "timezone")
    val timezone: Int,
    @Json(name = "visibility")
    val visibility: Int,
    @Json(name = "weather")
    val weather: List<Weather>,
    @Json(name = "wind")
    val wind: Wind
)