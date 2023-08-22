package com.gramzin.weather.domain.repository

import com.gramzin.weather.domain.model.City
import io.reactivex.rxjava3.core.Single

interface CityRepository {

    fun getCurrentCityName(latitude: Double, longitude: Double): Single<City>

    fun getCitiesByPartName(name: String): Single<List<City>>
}