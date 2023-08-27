package com.gramzin.weather.presentation.current_weather_screen

import com.gramzin.weather.domain.model.HourlyWeather
import com.gramzin.weather.domain.model.Weather

sealed interface CurrentWeatherScreenState {

    object Initial: CurrentWeatherScreenState

    object Loading: CurrentWeatherScreenState

    class WeatherLoaded(
        val city: String,
        val currentWeather: Weather,
        val forecast: List<HourlyWeather>
    ): CurrentWeatherScreenState
}