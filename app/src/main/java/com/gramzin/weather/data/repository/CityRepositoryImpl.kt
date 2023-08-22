package com.gramzin.weather.data.repository

import com.gramzin.weather.data.api.cities.service.CityService
import com.gramzin.weather.data.mapper.CityMapper
import com.gramzin.weather.domain.model.City
import com.gramzin.weather.domain.repository.CityRepository
import io.reactivex.rxjava3.core.Single

class CityRepositoryImpl(
    private val cityService: CityService,
    private val mapper: CityMapper
): CityRepository {

    override fun getCurrentCityName(latitude: Double, longitude: Double): Single<City> {
        return cityService.getCityByCoord(latitude, longitude).map {
            mapper.mapCityResponseToDomain(it)[0]
        }
    }

    override fun getCitiesByPartName(name: String): Single<List<City>> {
        TODO("Not yet implemented")
    }
}