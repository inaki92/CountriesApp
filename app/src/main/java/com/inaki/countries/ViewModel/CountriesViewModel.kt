package com.inaki.countries.ViewModel

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.inaki.countries.Model.CountriesModel
import com.inaki.countries.Network.CountryService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class CountriesViewModel(private val countryService: CountryService,
                         private val context: Context): ViewModel() {

    private var countryData: MutableLiveData<List<CountriesModel>>? = null

    fun getCountries(country: String): LiveData<List<CountriesModel>> {
        if (countryData == null){
            countryData = MutableLiveData()
            loadCountries(country)
        }
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
}