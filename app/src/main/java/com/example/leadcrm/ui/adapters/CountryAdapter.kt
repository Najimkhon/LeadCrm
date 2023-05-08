package com.example.leadcrm.ui.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.graphql.CountriesQuery
import com.example.graphql.LeadsQuery
import com.example.leadcrm.ui.layouts.CountryItemLayout
import com.example.leadcrm.ui.layouts.LeadItemLayout

class CountryAdapter(
    val context: Context
):RecyclerView.Adapter<CountryAdapter.CountryViewHolder>() {

    val diffCallback = object : DiffUtil.ItemCallback<CountriesQuery.FetchCountry>() {
        override fun areItemsTheSame(
            oldItem: CountriesQuery.FetchCountry,
            newItem: CountriesQuery.FetchCountry
        ): Boolean {
            return oldItem.id == newItem.id &&
                    oldItem.emoji == newItem.emoji &&
                    oldItem.title == newItem.title &&
                    oldItem.phoneCode == newItem.phoneCode
        }

        override fun areContentsTheSame(
            oldItem: CountriesQuery.FetchCountry,
            newItem: CountriesQuery.FetchCountry
        ): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    val differ = AsyncListDiffer(this, diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        return CountryViewHolder(CountryItemLayout(context))
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        val currentCountry = differ.currentList[position]
        holder.layout.fillContent(currentCountry)
    }

    fun setData(lessons: List<CountriesQuery.FetchCountry>) = differ.submitList(lessons)

    inner class CountryViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val layout = itemView as CountryItemLayout
    }
}