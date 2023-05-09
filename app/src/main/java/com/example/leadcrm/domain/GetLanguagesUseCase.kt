package com.example.leadcrm.domain

import com.example.graphql.LanguagesQuery
import javax.inject.Inject

class GetLanguagesUseCase @Inject constructor(
    private val leadsClient: LeadsClient
) {
    suspend fun execute(): List<LanguagesQuery.Language> {
        return leadsClient
            .getLanguages()
    }
}