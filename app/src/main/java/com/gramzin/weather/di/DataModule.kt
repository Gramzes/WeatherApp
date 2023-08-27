package com.gramzin.weather.di

import com.gramzin.weather.data.api.cities.service.CityService
import com.gramzin.weather.data.api.weather.service.WeatherService
import com.gramzin.weather.data.mapper.CityMapper
import com.gramzin.weather.data.mapper.WeatherMapper
import com.gramzin.weather.data.repository.CityRepositoryImpl
import com.gramzin.weather.data.repository.WeatherRepositoryImpl
import com.gramzin.weather.domain.repository.CityRepository
import com.gramzin.weather.domain.repository.WeatherRepository
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory


val dataModule = module {
    single {

        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client: OkHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()
        Retrofit.Builder()
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .baseUrl("http://api.openweathermap.org/")
            .build()
    }

    single {
        get<Retrofit>().create(CityService::class.java)
    }

    single {
        get<Retrofit>().create(WeatherService::class.java)
    }

    factory { CityMapper() }
    factory { WeatherMapper() }

    single<CityRepository> { CityRepositoryImpl(get(), get()) }
    single<WeatherRepository> { WeatherRepositoryImpl(get(), get()) }
}