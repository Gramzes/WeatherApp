package com.gramzin.weather.presentation.find_city_screen

import androidx.lifecycle.ViewModel
import com.gramzin.weather.domain.usecases.GetCurrentWeatherUseCase
import com.gramzin.weather.domain.usecases.GetHourlyForecastUseCase
import com.gramzin.weather.domain.usecases.GetUserCityUseCase
import com.gramzin.weather.domain.usecases.SearchCityUseCase

class FindCityViewModel(
    private val getUserCityUseCase: GetUserCityUseCase,
    private val searchCityUseCase: SearchCityUseCase
): ViewModel() {

}