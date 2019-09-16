package com.inaki.countries.Network

import com.inaki.countries.Model.CountriesModel
import com.inaki.countries.Util.Constants
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface CountryService {

    @GET(Constants.SEARCH_COUNTRY)
    fun countriesData(@Path("name") name: String): Observable<CountriesModel>
}