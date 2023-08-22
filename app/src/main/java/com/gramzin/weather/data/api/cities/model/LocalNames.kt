package com.gramzin.weather.data.api.cities.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LocalNames(
    @Json(name = "ru")
    val ru: String?,
)