package com.inaki.countries.DI

import com.inaki.countries.Network.CountryService
import com.inaki.countries.ViewModel.CountriesViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.create


val networkModule = module {
    single { retrofitClient(androidContext()).create<CountryService>() }
}

val viewModelModule = module {
    viewModel { CountriesViewModel(get(),androidContext()) }
}