package com.gramzin.weather.di

import com.gramzin.weather.domain.usecases.GetCurrentWeatherUseCase
import com.gramzin.weather.domain.usecases.GetDailyForecastUseCase
import com.gramzin.weather.domain.usecases.GetHourlyForecastUseCase
import com.gramzin.weather.domain.usecases.GetUserCityUseCase
import com.gramzin.weather.domain.usecases.SearchCityUseCase
import org.koin.dsl.module

val domainModule = module {
    factory { GetCurrentWeatherUseCase(get()) }
    factory { GetDailyForecastUseCase(get()) }
    factory { GetHourlyForecastUseCase(get()) }
    factory { GetUserCityUseCase(get()) }
    factory { SearchCityUseCase() }
}