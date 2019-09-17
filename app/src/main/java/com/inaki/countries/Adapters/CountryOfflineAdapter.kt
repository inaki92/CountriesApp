package com.inaki.countries.Adapters

import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.inaki.countries.R
import com.inaki.countries.RoomDB.CountriesEntity
import com.inaki.countries.Util.OnItemClick
import kotlinx.android.synthetic.main.country_items.view.*

class CountryOfflineAdapter(private val context: Context,
                            private val countriesOffline: List<CountriesEntity>,
                            private val countryClick: OnItemClick?):
RecyclerView.Adapter<CountryOfflineAdapter.OfflineViewHolder>() {

    class OfflineViewHolder(itemView: View, countryClick: OnItemClick):
        RecyclerView.ViewHolder(itemView), View.OnClickListener {

        private var onClickListener: OnItemClick

        init {
            itemView.setOnClickListener(this)
            onClickListener = countryClick
        }

        var countryName = itemView.country_name!!
        var flag = itemView.flag_img!!

        override fun onClick(v: View?) {
            onClickListener.itemClicked(countryName.text.toString())
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OfflineViewHolder {
        val countryView = LayoutInflater.from(context).inflate(R.layout.country_items,parent,false)
        return OfflineViewHolder(countryView, countryClick!!)
    }

    override fun getItemCount(): Int {
        return countriesOffline.size
    }

    override fun onBindViewHolder(holder: OfflineViewHolder, position: Int) {
        val country = countriesOffline[position]
        holder.countryName.text = country.name
        holder.flag.setImageBitmap(BitmapFactory.decodeFile(country.flagFile))
    }


}