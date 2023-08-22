package com.gramzin.weather.data.api.cities.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GetCityResponseItem(
    @Json(name = "country")
    val country: String,
    @Json(name = "lat")
    val lat: Double,
    @Json(name = "local_names")
    val localNames: LocalNames,
    @Json(name = "lon")
    val lon: Double,
    @Json(name = "name")
    val name: String,
    @Json(name = "state")
    val state: String
)