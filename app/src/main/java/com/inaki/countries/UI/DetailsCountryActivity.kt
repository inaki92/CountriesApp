package com.inaki.countries.UI

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou
import com.inaki.countries.Model.CountriesModel
import com.inaki.countries.R
import com.inaki.countries.RoomDB.CountriesEntity
import com.inaki.countries.ViewModel.CountriesViewModel
import kotlinx.android.synthetic.main.activity_details_country.*
import org.koin.android.viewmodel.ext.android.viewModel
import android.view.View
import kotlin.collections.ArrayList

class DetailsCountryActivity : AppCompatActivity() {

    private var countryName: String? = null
    private var countriesOffline: ArrayList<CountriesEntity> = ArrayList()
    private lateinit var detailsCountry: CountriesModel

    private val viewModel: CountriesViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_country)

        countryName = intent.getStringExtra("country")

        if (isNetworkAvailable()) {
            loadCountryDetails(countryName)
        }else{
            loadFromDB()
        }

        fav_btn.setOnClickListener {
            viewModel.insertCountry(CountriesEntity(
                name_country.text.removePrefix("Country:").toString(),
                capital_country.text.removePrefix("Capital:").toString(),
                calling_code.text.removePrefix("Calling Code:").toString(),
                region_country.text.removePrefix("Region:").toString(),
                sub_region_country.text.removePrefix("Sub Region:").toString(),
                time_zone.text.removePrefix("Time Zones").toString(),
                currencies_country.text.removePrefix("Currencies:").toString(),
                languages_country.text.removePrefix("Languages:").toString()
                ))
            Toast.makeText(this,"Country saved!",Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadCountryDetails(countryName: String?) {
        viewModel.getCountries(countryName!!).observe(this,Observer{
            detailsCountry = it.first()
            setUpItems(detailsCountry)
        })
    }

    private fun loadFromDB() {
        viewModel.getCountriesDB().observe(this,Observer{
            countriesOffline.addAll(it!!)
            setUpFromDB(countriesOffline)
        })
    }

    @SuppressLint("SetTextI18n")
    private fun setUpFromDB(countries: ArrayList<CountriesEntity>) {
        fav_btn.visibility = View.GONE
        for (item in countries.indices){
            if (countries[item].name == countryName){
                name_country.text = "Country: "+countries[item].name
                capital_country.text = "Capital: "+countries[item].capital
                calling_code.text = "Calling Code: "+countries[item].callingCodes
                region_country.text = "Region: "+countries[item].region
                sub_region_country.text = "Sub Region: "+countries[item].subRegion
                time_zone.text = "Time Zones: "+countries[item].timeZones
                currencies_country.text = "Currencies: "+countries[item].currencies
                languages_country.text = "Languages: "+countries[item].languages
            }
        }

    }

    @SuppressLint("SetTextI18n")
    private fun setUpItems(detailsCountry: CountriesModel) {
        name_country.text = "Country: "+detailsCountry.name
        capital_country.text = "Capital: "+detailsCountry.capital
        calling_code.text = "Calling Code: "+detailsCountry.callingCodes.joinToString()
        region_country.text = "Region: "+detailsCountry.region
        sub_region_country.text = "Sub Region: "+detailsCountry.subregion
        time_zone.text = "Time Zones: "+detailsCountry.timezones.joinToString()
        currencies_country.text = "Currencies: "+getCurrencies()
        languages_country.text = "Languages: "+getLanguages()

        GlideToVectorYou.justLoadImage(this,Uri.parse(detailsCountry.flag),flagIcon)
    }

    private fun getCurrencies(): String {
        val currencies = mutableListOf<String>()
        for (item in detailsCountry.currencies.indices){
            val currency = detailsCountry.currencies[item].name
            currencies.add(currency)
        }
        return currencies.joinToString()
    }

    private fun getLanguages(): String {
        val languages = mutableListOf<String>()
        for (item in detailsCountry.languages.indices){
            val currency = detailsCountry.languages[item].name
            languages.add(currency)
        }
        return languages.joinToString()
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE)
        return if (connectivityManager is ConnectivityManager) {
            val networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
            networkInfo?.isConnected ?: false
        } else false
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadCountry(true)
    }
}
