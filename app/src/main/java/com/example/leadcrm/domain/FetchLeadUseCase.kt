package com.example.leadcrm.domain

import com.example.graphql.FetchLeadQuery
import com.example.graphql.type.FetchLeadInput
import javax.inject.Inject

class FetchLeadUseCase @Inject constructor(
    private val leadsClient: LeadsClient
) {
    suspend fun execute(newLead: FetchLeadInput): FetchLeadQuery.FetchLead? {
        return leadsClient.getLead(newLead)
    }
}