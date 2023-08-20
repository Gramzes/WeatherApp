package com.gramzin.weather.presentation.current_weather_screen

import androidx.lifecycle.ViewModel
import com.gramzin.weather.domain.usecases.GetCurrentWeatherUseCase
import com.gramzin.weather.domain.usecases.GetHourlyForecastUseCase
import com.gramzin.weather.domain.usecases.GetUserCityUseCase

class CurrentWeatherViewModel(
    private val getUserCityUseCase: GetUserCityUseCase,
    private val getCurrentWeatherUseCase: GetCurrentWeatherUseCase,
    private val getHourlyForecastUseCase: GetHourlyForecastUseCase
): ViewModel() {

}