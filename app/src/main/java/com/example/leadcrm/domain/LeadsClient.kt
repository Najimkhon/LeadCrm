package com.example.leadcrm.domain

import com.example.graphql.LeadsQuery


interface LeadsClient {
   suspend fun getLeads(): LeadsQuery.FetchLeads
}