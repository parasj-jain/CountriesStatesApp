package com.paras.countrystate.country.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.paras.countrystate.R
import com.paras.countrystate.country.domain.CountrySelectedListener
import com.paras.countrystate.country.domain.model.Country
import com.paras.countrystate.databinding.ItemViewCountryBinding
import com.paras.countrystate.utils.Constants

class CountryAdapter(
    private val context: Context,
    private val countries: List<Country>
) : RecyclerView.Adapter<CountryAdapter.CountryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        return CountryViewHolder(
            ItemViewCountryBinding.bind(
                LayoutInflater.from(context).inflate(R.layout.item_view_country, parent, false)
            )
        )
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.setData(countries[position])
    }

    override fun getItemCount(): Int {
        return countries.size
    }

    inner class CountryViewHolder(private val binding: ItemViewCountryBinding) : RecyclerView.ViewHolder(binding.root) {

        fun setData(country: Country) {
            binding.countryName.text = country.name
            binding.countryName.visibility = View.VISIBLE
            binding.countryPhoneCode.text = context.getString(R.string.txt_phone_code_with_code, country.phoneCode)
            Glide.with(context).load(country.flagImage).into(binding.countryFlagImage)
            binding.root.setOnClickListener {
                (context as? CountrySelectedListener)?.onCountrySelected(country)
            }
        }

    }

}