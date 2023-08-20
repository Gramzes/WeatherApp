package com.gramzin.weather.domain.usecases

import com.gramzin.weather.domain.model.DailyWeather
import com.gramzin.weather.domain.model.Weather
import com.gramzin.weather.domain.repository.WeatherRepository
import io.reactivex.rxjava3.core.Single

class GetDailyForecastUseCase(
    private val repository: WeatherRepository
) {
    operator fun invoke(latitude: Double, longitude: Double): Single<List<DailyWeather>> {
        return repository.getDailyForecast(latitude, longitude)
    }
}