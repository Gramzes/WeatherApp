package com.gramzin.weather.data.mapper

import com.gramzin.weather.data.api.cities.model.GetCityResponse
import com.gramzin.weather.domain.model.City

class CityMapper {

    fun mapCityResponseToDomain(getCityResponse: GetCityResponse): List<City> {
        return getCityResponse.map {
            City(it.localNames.ru ?: it.name, it.lat, it.lon)
        }
    }
}