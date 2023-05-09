package com.example.leadcrm.ui.layouts

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.widget.RelativeLayout
import com.example.graphql.StatusQuery
import com.example.leadcrm.databinding.StatusItemViewBinding

class StatusItemLayout(context: Context) : RelativeLayout(context) {
    private val binding = StatusItemViewBinding.inflate(LayoutInflater.from(context), this, true)

    fun fillContent(status: StatusQuery.FetchLeadStatusType) {
        binding.cvStatusColor.setCardBackgroundColor(Color.parseColor(status.backgroundColor))
        binding.tvTitle.text = status.title
    }

}
