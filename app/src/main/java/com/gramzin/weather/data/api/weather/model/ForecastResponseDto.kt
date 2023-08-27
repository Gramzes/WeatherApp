package com.gramzin.weather.data.api.weather.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ForecastResponseDto(
    @Json(name = "list")
    val list: List<WeatherItem>,
    @Json(name = "city")
    val city: City
)