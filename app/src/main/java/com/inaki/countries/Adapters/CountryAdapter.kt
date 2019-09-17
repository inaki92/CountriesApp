package com.inaki.countries.Adapters

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou
import com.inaki.countries.Model.CountriesModel
import com.inaki.countries.R
import com.inaki.countries.Util.OnItemClick
import kotlinx.android.synthetic.main.country_items.view.*

class CountryAdapter(private val context: Context,
                     private val activity: Activity,
                     private val dataCountries: List<CountriesModel>,
                     private val countryClick: OnItemClick?)
    : RecyclerView.Adapter<CountryAdapter.CountryViewHolder>() {

    class CountryViewHolder(itemView: View, countryClick: OnItemClick):
        RecyclerView.ViewHolder(itemView), View.OnClickListener {

        private var onClickListener: OnItemClick

        init {
            itemView.setOnClickListener(this)
            onClickListener = countryClick
        }

        var countryName = itemView.country_name!!
        var countryFlag = itemView.flag_img!!

        override fun onClick(v: View?) {
            onClickListener.itemClicked(countryName.text.toString())
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val countryView = LayoutInflater.from(context).inflate(R.layout.country_items,parent,false)
        return CountryViewHolder(countryView, countryClick!!)
    }

    override fun getItemCount(): Int {
        return dataCountries.size
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        val country = dataCountries[position]

        holder.countryName.text = country.name

        GlideToVectorYou
            .justLoadImage(activity,Uri.parse(country.flag),holder.countryFlag)
    }
}