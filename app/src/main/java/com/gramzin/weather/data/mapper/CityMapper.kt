package com.gramzin.weather.data.mapper

import com.gramzin.weather.data.api.cities.model.GetCityResponse
import com.gramzin.weather.data.api.cities.model.GetCityResponseItem
import com.gramzin.weather.domain.model.City

class CityMapper {

    fun mapCityResponseToDomain(item: GetCityResponseItem): City {
        return City(item.localNames.ru ?: item.name, item.lat, item.lon)
    }
}