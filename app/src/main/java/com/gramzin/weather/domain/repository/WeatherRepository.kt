package com.gramzin.weather.domain.repository

import com.gramzin.weather.domain.model.DailyWeather
import com.gramzin.weather.domain.model.Weather
import io.reactivex.rxjava3.core.Single

interface WeatherRepository {

    fun getCurrentWeather(latitude: Double, longitude: Double): Single<Weather>

    fun getHourlyForecast(latitude: Double, longitude: Double): Single<List<Weather>>

    fun getDailyForecast(latitude: Double, longitude: Double): Single<List<DailyWeather>>
}