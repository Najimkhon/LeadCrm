package com.example.leadcrm.ui.layouts

import android.content.Context
import android.view.LayoutInflater
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat
import com.example.graphql.CountriesQuery
import com.example.graphql.LeadsQuery
import com.example.leadcrm.R
import com.example.leadcrm.databinding.CountryItemViewBinding
import com.example.leadcrm.databinding.LeadItemLayoutBinding
import com.example.leadcrm.utils.Constants.DEFAULT_EMOJI
import com.google.android.material.chip.Chip
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class CountryItemLayout(context: Context) : RelativeLayout(context) {
    private val binding = CountryItemViewBinding.inflate(LayoutInflater.from(context), this, true)

    fun fillContent(country: CountriesQuery.FetchCountry) {
        binding.tvEmoji.text = country.emoji
        binding.tvTitle.text = country.title
        binding.tvPhoneCode.text = country.phoneCode
    }

}
