package com.example.leadcrm.data

import com.apollographql.apollo3.ApolloClient
import com.example.graphql.*
import com.example.graphql.type.CreateLeadInput
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

    override suspend fun getStatusList(): List<StatusQuery.FetchLeadStatusType> {
        return apolloClient
            .query(StatusQuery())
            .execute()
            .data
            ?.fetchLeadStatusTypes
            ?: emptyList()
    }

    override suspend fun getLanguages(): List<LanguagesQuery.Language> {
        return apolloClient
            .query(LanguagesQuery())
            .execute()
            .data
            ?.languages
            ?: emptyList()
    }

    override suspend fun createLead(newLead: CreateLeadInput): CreateLeadMutation.CreateLead? {
        return apolloClient
            .mutation(CreateLeadMutation(newLead))
            .execute()
            .data
            ?.createLead
    }
}