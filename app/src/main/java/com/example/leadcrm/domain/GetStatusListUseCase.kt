package com.example.leadcrm.domain

import com.example.graphql.StatusQuery
import javax.inject.Inject

class GetStatusListUseCase @Inject constructor(
    private val leadsClient: LeadsClient
) {
    suspend fun execute(): List<StatusQuery.FetchLeadStatusType> {
        return leadsClient
            .getStatusList()
    }
}