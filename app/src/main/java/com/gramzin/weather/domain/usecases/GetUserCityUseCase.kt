package com.gramzin.weather.domain.usecases

import com.gramzin.weather.domain.model.City
import com.gramzin.weather.domain.repository.CityRepository
import io.reactivex.rxjava3.core.Single

class GetUserCityUseCase(
    private val cityRepository: CityRepository
) {

    operator fun invoke(latitude: Double, longitude: Double): Single<City> {
        return cityRepository.getCurrentCityName(latitude, longitude)
    }
}