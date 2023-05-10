package com.example.leadcrm.domain

import com.example.graphql.UpdateLeadMutation
import com.example.graphql.type.UpdateLeadInput
import javax.inject.Inject

class UpdateLeadUseCase @Inject constructor(
    private val leadsClient: LeadsClient
) {
    suspend fun execute(updatedLead: UpdateLeadInput): UpdateLeadMutation.UpdateLead? {
        return leadsClient.updateLead(updatedLead)
    }
}