package com.gramzin.weather.presentation.forecast_screen

import androidx.lifecycle.ViewModel
import com.gramzin.weather.domain.usecases.GetDailyForecastUseCase
import com.gramzin.weather.domain.usecases.GetUserCityUseCase

class ForecastViewModel(
    private val latitude: Double,
    private val longitude: Double,
    private val getDailyForecastUseCase: GetDailyForecastUseCase
): ViewModel() {

}