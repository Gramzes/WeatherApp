package com.gramzin.weather.domain

import com.gramzin.weather.R

sealed class WeatherType(
    val titleResId: Int,
    val dayIconResId: Int,
    val nightIconResId: Int
){

    object ClearSky: WeatherType(
        R.string.clear_sky,
        R.drawable.clear_sky_day,
        R.drawable.clear_sky_night
    )
    object FewClouds: WeatherType(
        R.string.few_clouds,
        R.drawable.few_clouds_day,
        R.drawable.few_clouds_night
    )
    object ScatteredClouds: WeatherType(
        R.string.scattered_clouds,
        R.drawable.few_clouds_day,
        R.drawable.few_clouds_night
    )
    object BrokenClouds: WeatherType(
        R.string.broken_clouds,
        R.drawable.clouds,
        R.drawable.clouds
    )
    object ShowerRain: WeatherType(
        R.string.shower_rain,
        R.drawable.shower_rain,
        R.drawable.shower_rain
    )
    object Rain: WeatherType(
        R.string.rain,
        R.drawable.rain_day,
        R.drawable.rain_night
    )
    object ThunderStorm: WeatherType(
        R.string.thunderstorm,
        R.drawable.thunderstorm,
        R.drawable.thunderstorm
    )
    object Snow: WeatherType(
        R.string.snow,
        R.drawable.snow,
        R.drawable.snow
    )
    object Mist: WeatherType(
        R.string.mist,
        R.drawable.clouds,
        R.drawable.clouds
    )
}