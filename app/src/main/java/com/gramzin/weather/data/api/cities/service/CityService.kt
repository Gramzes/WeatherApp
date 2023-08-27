package com.gramzin.weather.data.api.cities.service

import com.gramzin.weather.data.api.cities.model.GetCityResponse
import com.gramzin.weather.data.api.cities.model.GetCityResponseItem
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface CityService {

    @GET("geo/1.0/reverse?&limit=1&appid=3a59407b2c30e2891e8f86ce591bde6f")
    fun getCityByCoord(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double
    ): Single<List<GetCityResponseItem>>
}