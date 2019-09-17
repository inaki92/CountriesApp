package com.inaki.countries.DI

import androidx.room.Room
import com.inaki.countries.Network.CountryService
import com.inaki.countries.RoomDB.CountriesDatabase
import com.inaki.countries.RoomDB.LocalData
import com.inaki.countries.ViewModel.CountriesViewModel
import io.reactivex.disposables.CompositeDisposable
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.create


val networkModule = module {
    single { retrofitClient(androidContext()).create<CountryService>() }
}

val viewModelModule = module {
    viewModel { CountriesViewModel(get(),get(),androidContext()) }
}

val dbModule = module {
    single { Room.databaseBuilder(androidContext(),
        CountriesDatabase::class.java, "countriesdata.db")
        .build().countriesDao() }

    single { CompositeDisposable() }
}

val localDataModule = module {
    single { LocalData(get(),get()) }
}