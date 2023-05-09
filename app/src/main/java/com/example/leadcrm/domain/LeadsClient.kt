package com.example.leadcrm.domain

import com.example.graphql.*
import com.example.graphql.type.CreateLeadInput
import com.example.graphql.type.FetchLeadInput


interface LeadsClient {
    suspend fun getLeads(): LeadsQuery.FetchLeads
    suspend fun getCountries(): List<CountriesQuery.FetchCountry>
    suspend fun getStatusList(): List<StatusQuery.FetchLeadStatusType>
    suspend fun getLanguages(): List<LanguagesQuery.Language>
    suspend fun createLead(newLead: CreateLeadInput): CreateLeadMutation.CreateLead?
    suspend fun getLead(lead: FetchLeadInput): FetchLeadQuery.FetchLead?
}