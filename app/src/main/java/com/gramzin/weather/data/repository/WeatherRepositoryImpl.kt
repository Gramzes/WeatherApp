package com.gramzin.weather.data.repository

import com.gramzin.weather.data.api.weather.service.WeatherService
import com.gramzin.weather.data.mapper.WeatherMapper
import com.gramzin.weather.domain.model.DailyWeather
import com.gramzin.weather.domain.model.HourlyWeather
import com.gramzin.weather.domain.model.Weather
import com.gramzin.weather.domain.repository.WeatherRepository
import io.reactivex.rxjava3.core.Single

class WeatherRepositoryImpl(
    private val mapper: WeatherMapper,
    private val weatherService: WeatherService
): WeatherRepository {

    override fun getCurrentWeather(latitude: Double, longitude: Double): Single<Weather> {
        return weatherService.getCurrentWeather(latitude, longitude).map {
            mapper.mapWeatherToDomain(it)
        }
    }

    override fun getHourlyForecast(
        latitude: Double, longitude: Double
    ): Single<List<HourlyWeather>> {
        return weatherService.getForecast(latitude, longitude).map { response ->
            mapper.mapHourlyForecastToDomain(response)
        }
    }

    override fun getDailyForecast(latitude: Double, longitude: Double): Single<List<DailyWeather>> {
        return weatherService.getForecast(latitude, longitude).map { response ->
            mapper.mapDailyWeatherToDomain(response)
        }
    }
}