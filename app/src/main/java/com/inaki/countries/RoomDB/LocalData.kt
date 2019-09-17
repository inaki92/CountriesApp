package com.inaki.countries.RoomDB

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class LocalData(private val countriesDao: CountriesDao,
                private val compositeDisposable: CompositeDisposable) {

    fun getAllData() : LiveData<List<CountriesEntity>> {
        return LiveDataReactiveStreams.fromPublisher(countriesDao.getAll()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.computation()))
    }

    fun insertData(countriesEntity: CountriesEntity){
        compositeDisposable.add(Observable.fromCallable {countriesDao.insert(countriesEntity)}
            .subscribeOn(Schedulers.computation())
            .subscribe())
    }

    fun deleteData(country: CountriesEntity){
        compositeDisposable.add(Observable.fromCallable { countriesDao.delete(country) }
            .subscribeOn(Schedulers.computation())
            .subscribe())
    }
}