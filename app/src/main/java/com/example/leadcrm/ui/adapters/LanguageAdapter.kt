package com.example.leadcrm.ui.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.graphql.LanguagesQuery
import com.example.leadcrm.ui.layouts.LanguageItemLayout

class LanguageAdapter(
    val context: Context
) : RecyclerView.Adapter<LanguageAdapter.LanguageViewHolder>() {

    val diffCallback = object : DiffUtil.ItemCallback<LanguagesQuery.Language>() {
        override fun areItemsTheSame(
            oldItem: LanguagesQuery.Language, newItem: LanguagesQuery.Language
        ): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(
            oldItem: LanguagesQuery.Language, newItem: LanguagesQuery.Language
        ): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    val differ = AsyncListDiffer(this, diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LanguageViewHolder {
        return LanguageViewHolder(LanguageItemLayout(context))
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: LanguageViewHolder, position: Int) {
        val currentLanguage = differ.currentList[position]
        holder.layout.fillContent(currentLanguage)
    }

    fun setData(languageList: List<LanguagesQuery.Language>) = differ.submitList(languageList)

    inner class LanguageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val layout = itemView as LanguageItemLayout
    }
}