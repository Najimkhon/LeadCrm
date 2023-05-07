package com.example.leadcrm.ui.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.graphql.LeadsQuery
import com.example.leadcrm.ui.layouts.LeadItemLayout

class LeadAdapter(
    val context: Context
) : RecyclerView.Adapter<LeadAdapter.LeadViewHolder>() {

    val diffCallback = object : DiffUtil.ItemCallback<LeadsQuery.Data1>() {
        override fun areItemsTheSame(
            oldItem: LeadsQuery.Data1, newItem: LeadsQuery.Data1
        ): Boolean {
            return oldItem.id == newItem.id &&
                    oldItem.firstName == newItem.firstName &&
                    oldItem.lastName == newItem.lastName &&
                    oldItem.createdAt == newItem.createdAt &&
                    oldItem.updatedAt == newItem.updatedAt &&
                    oldItem.adSource == newItem.adSource
        }

        override fun areContentsTheSame(
            oldItem: LeadsQuery.Data1, newItem: LeadsQuery.Data1
        ): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    val differ = AsyncListDiffer(this, diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeadViewHolder {
        return LeadViewHolder(LeadItemLayout(context))
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: LeadViewHolder, position: Int) {
        val currentLead = differ.currentList[position]
        holder.layout.fillContent(currentLead)
    }

    fun setData(lessons: List<LeadsQuery.Data1>) = differ.submitList(lessons)

    inner class LeadViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val layout = itemView as LeadItemLayout
    }
}