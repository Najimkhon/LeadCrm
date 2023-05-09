package com.example.leadcrm.ui.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.graphql.CountriesQuery
import com.example.graphql.LeadsQuery
import com.example.graphql.StatusQuery
import com.example.leadcrm.ui.layouts.CountryItemLayout
import com.example.leadcrm.ui.layouts.StatusItemLayout

class StatusAdapter(
    val context: Context
) : RecyclerView.Adapter<StatusAdapter.StatusViewHolder>() {

    val diffCallback = object : DiffUtil.ItemCallback<StatusQuery.FetchLeadStatusType>() {
        override fun areItemsTheSame(
            oldItem: StatusQuery.FetchLeadStatusType, newItem: StatusQuery.FetchLeadStatusType
        ): Boolean {
            return oldItem.title == newItem.title &&
                    oldItem.step == newItem.step &&
                    oldItem.stepsCount == newItem.stepsCount &&
                    oldItem.backgroundColor == newItem.backgroundColor
        }

        override fun areContentsTheSame(
            oldItem: StatusQuery.FetchLeadStatusType, newItem: StatusQuery.FetchLeadStatusType
        ): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    val differ = AsyncListDiffer(this, diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatusViewHolder {
        return StatusViewHolder(StatusItemLayout(context))
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: StatusViewHolder, position: Int) {
        val currentStatus = differ.currentList[position]
        holder.layout.fillContent(currentStatus)
    }

    fun setData(statusList: List<StatusQuery.FetchLeadStatusType>) = differ.submitList(statusList)

    inner class StatusViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val layout = itemView as StatusItemLayout
    }
}