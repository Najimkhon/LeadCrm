package com.example.leadcrm.ui.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.graphql.CountriesQuery
import com.example.leadcrm.ui.layouts.CountryItemLayout
import java.util.*

class CountryAdapter(
    val context: Context
):RecyclerView.Adapter<CountryAdapter.CountryViewHolder>() {

    private val originalList = mutableListOf<CountriesQuery.FetchCountry>()

    private val displayedFilteredList = mutableListOf<CountriesQuery.FetchCountry>()

    private val notYetDisplayedFilteredList = mutableListOf<CountriesQuery.FetchCountry>()

    val diffCallback = object : DiffUtil.ItemCallback<CountriesQuery.FetchCountry>() {
        override fun areItemsTheSame(
            oldItem: CountriesQuery.FetchCountry,
            newItem: CountriesQuery.FetchCountry
        ): Boolean {
            return oldItem.id == newItem.id &&
                    oldItem.title == newItem.title

        }

        override fun areContentsTheSame(
            oldItem: CountriesQuery.FetchCountry,
            newItem: CountriesQuery.FetchCountry
        ): Boolean {
            return  oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        return CountryViewHolder(CountryItemLayout(context))
    }

    override fun getItemCount(): Int {
        val size = notYetDisplayedFilteredList.size
        println("hop: getItemCount = $size")
        return size
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        val currentCountry = notYetDisplayedFilteredList[position]
        holder.layout.fillContent(currentCountry)
    }

    fun setData(countries: List<CountriesQuery.FetchCountry>) {
        originalList.clear()
        originalList.addAll(countries)
        filter("")
    }

    fun filter(query: String) {
        notYetDisplayedFilteredList.clear()
        println("hop: query=$query")
        if (query.trim().isEmpty()) {
            notYetDisplayedFilteredList.addAll(originalList)
        } else {
            val lowerCaseQuery = query.trim().lowercase(Locale.getDefault())
            for (country in originalList) {
                if (country.title.lowercase(Locale.getDefault()).contains(lowerCaseQuery)) {
                    notYetDisplayedFilteredList.add(country)
                }
            }
            println("hop: result size=${notYetDisplayedFilteredList.size}")
        }

        val diffResult = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun getOldListSize(): Int {
                val oldListSize = displayedFilteredList.size
                println("hop: oldListSize = $oldListSize")
                return oldListSize
            }

            override fun getNewListSize(): Int {
                val newListSize = notYetDisplayedFilteredList.size
                println("hop: newListSize = $newListSize")
                return newListSize
            }

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                val oldItem = displayedFilteredList[oldItemPosition]
                val newItem = notYetDisplayedFilteredList[newItemPosition]
                println("hop: areItemsTheSame:oldItem = $oldItem")
                println("hop: areItemsTheSame:newItem = $newItem")
                val areItemsTheSame = oldItem.id == newItem.id
                println("hop: areItemsTheSame result = $areItemsTheSame")
                println("hop: =====================")
                return areItemsTheSame
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                val oldItem = displayedFilteredList[oldItemPosition]
                val newItem = notYetDisplayedFilteredList[newItemPosition]
                println("hop: areContentsTheSame:oldItem = $oldItem")
                println("hop: areContentsTheSame:newItem = $newItem")
                val areContentsTheSame = oldItem == newItem
                println("hop: areContentsTheSame result = $areContentsTheSame")
                println("hop: =====================")
                return areContentsTheSame
            }
        })

        diffResult.dispatchUpdatesTo(this)
        displayedFilteredList.clear()
        displayedFilteredList.addAll(notYetDisplayedFilteredList)
        println("hop: =======================================================")
        println("hop: =======================================================")
    }

    inner class CountryViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val layout = itemView as CountryItemLayout
    }
}