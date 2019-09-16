package com.inaki.countries.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.inaki.countries.Model.CountriesModel
import com.inaki.countries.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.country_items.view.*

class CountryAdapter(private val context: Context,
                     private val dataCountries: List<CountriesModel>)
    : RecyclerView.Adapter<CountryAdapter.CountryViewHolder>() {

    class CountryViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var countryName = itemView.country_name!!
        var countryFlag = itemView.flag_img!!
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CountryViewHolder {
        val countryView = LayoutInflater.from(context).inflate(R.layout.country_items,parent,false)
        return CountryViewHolder(countryView)
    }

    override fun getItemCount(): Int {
        return dataCountries.size
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        val country = dataCountries[position]

        holder.countryName.text = country.name

        Picasso.get()
            .load(country.flag)
            .resize(50,50)
            .centerCrop()
            .into(holder.countryFlag)
    }
}