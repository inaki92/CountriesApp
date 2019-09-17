package com.inaki.countries.UI

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SearchView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.inaki.countries.Adapters.CountryAdapter
import com.inaki.countries.Adapters.CountryOfflineAdapter
import com.inaki.countries.Model.CountriesModel
import com.inaki.countries.R
import com.inaki.countries.RoomDB.CountriesEntity
import com.inaki.countries.Util.OnItemClick
import com.inaki.countries.ViewModel.CountriesViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(), OnItemClick {

    private lateinit var listCountries: List<CountriesModel>
    private val countriesOffline : ArrayList<CountriesEntity> = ArrayList()
    private lateinit var countryAdapter: CountryAdapter
    private lateinit var countryOfflineAdapter: CountryOfflineAdapter

    private val countriesViewModel: CountriesViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        if (savedInstanceState == null) {
            recycler_country.setHasFixedSize(true)
            recycler_country.layoutManager = LinearLayoutManager(
                this,
                LinearLayoutManager.VERTICAL, false
            )

            if (isNetworkAvailable()) {
                search_country.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        return false
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        loadCountries(newText)
                        return true
                    }

                })
            } else {
                countriesViewModel.loadCountry(true)
                search_country.visibility = View.INVISIBLE
                Log.d("main","ONCREATE")
                countriesViewModel.getCountriesDB().observe(this, Observer {
                    if (countriesOffline != it) {
                        countriesOffline.addAll(it)
                        countryOfflineAdapter = CountryOfflineAdapter(
                            this,
                            countriesOffline, this
                        )
                        recycler_country.adapter = countryOfflineAdapter
                    }
                })
            }
        }
    }

    private fun loadCountries(country: String?) {
        countriesViewModel.getCountries(country!!).observe(this, Observer { countriesFetch ->
            listCountries = countriesFetch
            countryAdapter = CountryAdapter(this,this, listCountries, this)
            recycler_country.adapter = countryAdapter
        })
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE)
        return if (connectivityManager is ConnectivityManager) {
            val networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
            networkInfo?.isConnected ?: false
        } else false
    }

    override fun itemClicked(countryName: String) {
        val intent = Intent(this, DetailsCountryActivity::class.java)
        intent.putExtra("country", countryName)
        startActivity(intent)
    }
}
