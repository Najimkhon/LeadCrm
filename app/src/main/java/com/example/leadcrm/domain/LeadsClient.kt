package com.example.leadcrm.domain

import com.example.graphql.CountriesQuery
import com.example.graphql.LeadsQuery


interface LeadsClient {
   suspend fun getLeads(): LeadsQuery.FetchLeads
   suspend fun getCountries(): List<CountriesQuery.FetchCountry>
}