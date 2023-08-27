package com.gramzin.weather.data.api.weather.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class City(
    @Json(name = "timezone")
    val timezone: Long
)
