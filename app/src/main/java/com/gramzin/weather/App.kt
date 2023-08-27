package com.gramzin.weather

import android.app.Application
import com.gramzin.weather.di.dataModule
import com.gramzin.weather.di.domainModule
import com.gramzin.weather.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class App: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin{
            androidLogger()
            androidContext(this@App)
            modules(dataModule, domainModule, presentationModule)
        }
    }
}