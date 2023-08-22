package com.gramzin.weather.data.api.cities.service

import com.gramzin.weather.data.api.cities.model.GetCityResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface CityService {

    @GET("http://api.openweathermap.org/geo/1.0/reverse?&limit=1&appid={API key}")
    fun getCityByCoord(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double
    ): Single<GetCityResponse>
}