package com.example.leadcrm.ui.layouts

import android.content.Context
import android.view.LayoutInflater
import android.widget.RelativeLayout
import com.example.graphql.CountriesQuery
import com.example.leadcrm.databinding.CountryItemViewBinding

class CountryItemLayout(context: Context, private val listener: OnItemClickListener) :
    RelativeLayout(context) {
    private val binding = CountryItemViewBinding.inflate(LayoutInflater.from(context), this, true)

    fun fillContent(country: CountriesQuery.FetchCountry) {
        binding.tvEmoji.text = country.emoji
        binding.tvTitle.text = country.title
        binding.tvPhoneCode.text = country.phoneCode
        binding.itemView.setOnClickListener {
            listener.onItemClicked(country.id, country.title)
        }
    }

    interface OnItemClickListener {
        fun onItemClicked(countryId: Int, countryTitle: String)
    }

}
