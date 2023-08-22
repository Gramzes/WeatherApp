package com.gramzin.weather.data.api.weather.service

import com.gramzin.weather.data.api.weather.model.ForecastResponseDto
import com.gramzin.weather.data.api.weather.model.WeatherItem
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET("api.openweathermap.org/data/2.5/weather?units=metric&appid={API key}")
    fun getCurrentWeather(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double
    ): Single<WeatherItem>

    @GET("api.openweathermap.org/data/2.5/forecast?units=metric&appid={API key}")
    fun getForecast(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double
    ): Single<ForecastResponseDto>
}