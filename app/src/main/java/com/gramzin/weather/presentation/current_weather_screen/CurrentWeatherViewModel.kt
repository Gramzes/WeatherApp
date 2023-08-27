package com.gramzin.weather.presentation.current_weather_screen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gramzin.weather.domain.usecases.GetCurrentWeatherUseCase
import com.gramzin.weather.domain.usecases.GetHourlyForecastUseCase
import com.gramzin.weather.domain.usecases.GetUserCityUseCase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers


class CurrentWeatherViewModel(
    private val getUserCityUseCase: GetUserCityUseCase,
    private val getCurrentWeatherUseCase: GetCurrentWeatherUseCase,
    private val getHourlyForecastUseCase: GetHourlyForecastUseCase
): ViewModel() {

    private val _state = MutableLiveData<CurrentWeatherScreenState>(CurrentWeatherScreenState.Initial)
    val state: LiveData<CurrentWeatherScreenState> = _state

    private val compositeDisposable = CompositeDisposable()

    fun getData(latitude: Double, longitude: Double){
        val weatherSingle = getCurrentWeatherUseCase(latitude, longitude)
        val citySingle = getUserCityUseCase(latitude, longitude)
        val forecastSingle = getHourlyForecastUseCase(latitude, longitude)

        _state.value = CurrentWeatherScreenState.Loading

        val disposable = Single
            .zip(citySingle, weatherSingle, forecastSingle) { city, weather, forecast ->
                _state.postValue(CurrentWeatherScreenState.WeatherLoaded(
                    city = city.cityName,
                    currentWeather = weather,
                    forecast = forecast
                ))
            }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .onErrorReturn {
                Log.d("TEST", it.message ?: "Error")
            }
            .subscribe()

        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
        compositeDisposable.clear()
    }
}