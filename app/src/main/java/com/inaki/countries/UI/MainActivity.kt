package com.inaki.countries.UI

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SearchView
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.inaki.countries.Adapter.CountryAdapter
import com.inaki.countries.Model.CountriesModel
import com.inaki.countries.R
import com.inaki.countries.Util.OnItemClick
import com.inaki.countries.ViewModel.CountriesViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(), OnItemClick {

    private lateinit var listCountries: List<CountriesModel>
    private lateinit var countryAdapter: CountryAdapter

    private val countriesViewModel: CountriesViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recycler_country.setHasFixedSize(true)
        recycler_country.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)

        search_country.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                loadCountries(newText)
                return true
            }

        })
    }

    private fun loadCountries(country: String?) {
        countriesViewModel.getCountries(country!!).observe(this, Observer { countriesFetch ->
            listCountries = countriesFetch
            countryAdapter = CountryAdapter(this,this, listCountries, this)
            recycler_country.adapter = countryAdapter
        })
    }

    override fun itemClicked(countryName: String) {
        val intent = Intent(this,DetailsCountryActivity::class.java)
        intent.putExtra("country",countryName)
        startActivity(intent)
    }
}
