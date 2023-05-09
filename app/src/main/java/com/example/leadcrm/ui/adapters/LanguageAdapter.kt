package com.example.leadcrm.ui.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.graphql.LanguagesQuery
import com.example.leadcrm.ui.dialogs.LanguagesDialogFragment
import com.example.leadcrm.ui.layouts.LanguageItemLayout

class LanguageAdapter(
    val context: Context,
    private val listener: LanguageItemLayout.OnClickListener,
) : RecyclerView.Adapter<LanguageAdapter.LanguageViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<LanguagesQuery.Language>() {
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

    private val differ = AsyncListDiffer(this, diffCallback)
    var selectionList: MutableList<LanguagesDialogFragment.LanguageSelectionHolder>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LanguageViewHolder {
        return LanguageViewHolder(LanguageItemLayout(context, listener))
    }

    override fun onBindViewHolder(holder: LanguageViewHolder, position: Int) {
        val currentLanguage = differ.currentList[position]
        val isSelected = selectionList?.find { it.id == currentLanguage.id }?.isSelected ?: false

        val helperItem = selectionList?.find { it.id == currentLanguage.id }
        val helperSelected = helperItem?.isSelected ?: false

        holder.layout.fillContent(currentLanguage, position, isSelected)
        println("hop: onBindViewHolder: position=$position, helperItem = $helperItem, helperSelected = $helperSelected,  ")

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun setData(languageList: List<LanguagesQuery.Language>) = differ.submitList(languageList)

    inner class LanguageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val layout = itemView as LanguageItemLayout
    }
}