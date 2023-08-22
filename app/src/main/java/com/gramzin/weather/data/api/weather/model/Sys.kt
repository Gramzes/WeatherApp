package com.gramzin.weather.data.api.weather.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Sys(
    @Json(name = "sunrise")
    val sunrise: Long,
    @Json(name = "sunset")
    val sunset: Long,
    @Json(name = "type")
    val type: Int
)