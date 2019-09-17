package com.inaki.countries

import android.app.Application
import com.inaki.countries.DI.dbModule
import com.inaki.countries.DI.localDataModule
import com.inaki.countries.DI.networkModule
import com.inaki.countries.DI.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class CountriesApp: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@CountriesApp)
            modules(listOf(networkModule, viewModelModule, localDataModule, dbModule))
        }
    }
}