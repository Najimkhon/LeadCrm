package com.example.leadcrm.ui.layouts

import android.content.Context
import android.view.LayoutInflater
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat
import com.example.graphql.LeadsQuery
import com.example.leadcrm.R
import com.example.leadcrm.databinding.LeadItemLayoutBinding
import com.example.leadcrm.utils.Constants.DEFAULT_EMOJI
import com.google.android.material.chip.Chip
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class LeadItemLayout(context: Context) : RelativeLayout(context) {
    private val binding = LeadItemLayoutBinding.inflate(LayoutInflater.from(context), this, true)
    private val isoFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    private val dateFormatter = SimpleDateFormat("dd/MM/yyyy", Locale.US)

    fun fillContent(lead: LeadsQuery.Data1) {
        var updatedDate = ""
        var createdDate = ""
        try {
            val updatedApiDate = isoFormat.parse(lead.updatedAt.toString())
            updatedDate = dateFormatter.format(updatedApiDate)
            val createdApiDate = isoFormat.parse(lead.updatedAt.toString())
            createdDate = dateFormatter.format(createdApiDate)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        val firstNameInit = lead.firstName!![0].toString()
        val lastNameInit = lead.lastName!![0].toString()

        binding.apply {
            tvCreatedDate.text = "Created date: $createdDate"
            tvUpdatedDate.text = "Updated date: $updatedDate"
            tvName.text = lead.firstName + " " + lead.lastName
            tvProfileInitials.text = firstNameInit + lastNameInit
            if (lead.country?.emoji != null) {
                tvEmoji.text = lead.country.emoji
            } else {
                tvEmoji.text = DEFAULT_EMOJI
            }
        }

        setChipGroup(lead.adSource, lead.intention, lead.status, lead.channelSource)
    }

    private fun setChipGroup(
        adSources: LeadsQuery.AdSource?,
        intention: LeadsQuery.Intention?,
        status: LeadsQuery.Status?,
        channel: LeadsQuery.ChannelSource?
    ) {
        binding.adSources.removeAllViews()
        intention?.let {
            makeChip(it.title)
        }
        status?.let {
            makeChip(it.title)
        }
        adSources?.let {
            makeChip(it.title)
        }
        channel?.let {
            makeChip(it.title)
        }
    }

    private fun makeChip(text: String) {
        val chip = Chip(binding.adSources.context)
        chip.apply {
            isClickable = true
            isCheckable = false
            setChipBackgroundColorResource(R.color.tag_bg_color)
            setTextAppearance(R.style.ChipStyle)
            setTextColor(ContextCompat.getColor(binding.adSources.context, R.color.grey))
            chip.text = text
        }
        binding.adSources.addView(chip)
    }
}
