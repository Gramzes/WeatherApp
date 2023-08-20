package com.gramzin.weather.domain.usecases

import com.gramzin.weather.domain.model.Weather
import com.gramzin.weather.domain.repository.WeatherRepository
import io.reactivex.rxjava3.core.Single

class GetCurrentWeatherUseCase(
    private val repository: WeatherRepository
) {

    operator fun invoke(latitude: Double, longitude: Double): Single<Weather> {
        return repository.getCurrentWeather(latitude, longitude)
    }
}