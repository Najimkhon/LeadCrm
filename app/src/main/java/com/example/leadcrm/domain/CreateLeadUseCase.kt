package com.example.leadcrm.domain

import com.example.graphql.CreateLeadMutation
import com.example.graphql.type.CreateLeadInput
import javax.inject.Inject

class CreateLeadUseCase @Inject constructor(
    private val leadsClient: LeadsClient
) {
    suspend fun execute(newLead: CreateLeadInput): CreateLeadMutation.CreateLead? {
        return leadsClient.createLead(newLead)
    }
}