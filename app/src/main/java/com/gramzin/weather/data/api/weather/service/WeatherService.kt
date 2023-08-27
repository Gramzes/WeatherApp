package com.gramzin.weather.data.api.weather.service

import com.gramzin.weather.data.api.weather.model.ForecastResponseDto
import com.gramzin.weather.data.api.weather.model.WeatherItem
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET("data/2.5/weather?units=metric&appid=3a59407b2c30e2891e8f86ce591bde6f")
    fun getCurrentWeather(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double
    ): Single<WeatherItem>

    @GET("data/2.5/forecast?units=metric&appid=3a59407b2c30e2891e8f86ce591bde6f")
    fun getForecast(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double
    ): Single<ForecastResponseDto>
}