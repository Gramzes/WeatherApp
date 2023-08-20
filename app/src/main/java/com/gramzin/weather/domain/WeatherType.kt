package com.gramzin.weather.domain

sealed interface WeatherType{

    object ClearSky: WeatherType
    object FewClouds: WeatherType
    object ScatteredClouds: WeatherType
    object BrokenClouds: WeatherType
    object ShowerRain: WeatherType
    object Rain: WeatherType
    object ThunderStorm: WeatherType
    object Snow: WeatherType
    object Mist: WeatherType
}