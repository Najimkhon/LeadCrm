package com.example.leadcrm.data

import com.apollographql.apollo3.ApolloClient
import com.example.graphql.CountriesQuery
import com.example.graphql.LeadsQuery
import com.example.leadcrm.domain.LeadsClient
import javax.inject.Inject

class ApolloLeadsClient @Inject constructor(
    private val apolloClient: ApolloClient
):LeadsClient {
    override suspend fun getLeads(): LeadsQuery.FetchLeads {
        return apolloClient
            .query(LeadsQuery())
            .execute()
            .data
            ?.fetchLeads
            ?: LeadsQuery.FetchLeads(emptyList())
    }

    override suspend fun getCountries(): List<CountriesQuery.FetchCountry> {
        return apolloClient
            .query(CountriesQuery())
            .execute()
            .data
            ?.fetchCountries
            ?: emptyList()
    }
}