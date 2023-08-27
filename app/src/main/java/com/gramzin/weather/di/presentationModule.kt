package com.gramzin.weather.di

import com.gramzin.weather.presentation.current_weather_screen.CurrentWeatherViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val presentationModule = module {
    viewModelOf(::CurrentWeatherViewModel)
}