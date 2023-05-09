package com.example.leadcrm.ui.layouts

import android.content.Context
import android.view.LayoutInflater
import android.widget.RelativeLayout
import com.example.graphql.LanguagesQuery
import com.example.leadcrm.databinding.LanguageItemViewBinding

class LanguageItemLayout(context: Context) : RelativeLayout(context) {
    private val binding = LanguageItemViewBinding.inflate(LayoutInflater.from(context), this, true)

    fun fillContent(language: LanguagesQuery.Language) {
        binding.tvTitle.text = language.title
    }

}
