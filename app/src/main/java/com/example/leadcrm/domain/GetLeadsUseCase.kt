package com.example.leadcrm.domain

import com.example.graphql.LeadsQuery
import javax.inject.Inject

class GetLeadsUseCase @Inject constructor(
    private val leadsClient: LeadsClient
) {
    suspend fun execute(): LeadsQuery.FetchLeads {
        return leadsClient
            .getLeads()
    }
}