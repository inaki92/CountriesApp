package com.inaki.countries.UI

import android.annotation.SuppressLint
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou
import com.inaki.countries.Model.CountriesModel
import com.inaki.countries.R
import com.inaki.countries.ViewModel.CountriesViewModel
import kotlinx.android.synthetic.main.activity_details_country.*
import org.koin.android.viewmodel.ext.android.viewModel

class DetailsCountryActivity : AppCompatActivity() {

    private var countryName: String? = null
    private lateinit var detailsCountry: CountriesModel

    private val viewModel: CountriesViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_country)

        countryName = intent.getStringExtra("country")

        loadCountryDetails(countryName)
    }

    private fun loadCountryDetails(countryName: String?) {
        viewModel.getCountries(countryName!!).observe(this,Observer{
            detailsCountry = it.first()
            setUpItems(detailsCountry)
        })
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
}
