package com.example.leadcrm.ui.layouts

import android.content.Context
import android.view.LayoutInflater
import android.widget.RelativeLayout
import androidx.core.view.isVisible
import com.example.graphql.LanguagesQuery
import com.example.leadcrm.databinding.LanguageItemViewBinding

class LanguageItemLayout(
    context: Context, private val listener: OnClickListener,
) :
    RelativeLayout(context) {
    private val binding = LanguageItemViewBinding.inflate(LayoutInflater.from(context), this, true)

    fun fillContent(language: LanguagesQuery.Language, position: Int, isSelected: Boolean) {
        binding.tvTitle.text = language.title
        binding.ivSelected.isVisible = isSelected
        binding.itemView.setOnClickListener {
            println("hop: click qildim item layuotda")
            listener.onItemClicked(language.id, position)
        }

    }

    interface OnClickListener {
        fun onItemClicked(languageId: Int, position: Int)
    }

}
