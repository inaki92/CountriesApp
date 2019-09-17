package com.inaki.countries.ViewModel

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.inaki.countries.Model.CountriesModel
import com.inaki.countries.Network.CountryService
import com.inaki.countries.RoomDB.CountriesEntity
import com.inaki.countries.RoomDB.LocalData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class CountriesViewModel(private val countryService: CountryService,
                         private val localData: LocalData,
                         private val context: Context): ViewModel() {

    private var countryData: MutableLiveData<List<CountriesModel>>? = null
    private var mTriggerFetchData = MutableLiveData<Boolean>()
    private var country : LiveData<List<CountriesEntity>> = Transformations.switchMap(mTriggerFetchData){
        localData.getAllData()
    }

    fun getCountries(country: String): LiveData<List<CountriesModel>> {
        countryData = MutableLiveData()
        loadCountries(country)
        return countryData!!
    }

    @SuppressLint("CheckResult")
    private fun loadCountries(country: String) {
        countryService.countriesData(country)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = { countriesModel -> countryData!!.value = countriesModel },
                onError = { Toast.makeText(context,it.message,Toast.LENGTH_SHORT).show() }
            )
    }

    fun insertCountry(country: CountriesEntity) {
        localData.insertData(country)
    }

    fun getCountriesDB(): LiveData<List<CountriesEntity>> {
        return country
    }

    fun loadCountry(state: Boolean) {
        mTriggerFetchData.value = state
    }


}