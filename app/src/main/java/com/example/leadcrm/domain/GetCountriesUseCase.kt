package com.example.leadcrm.domain

import com.example.graphql.CountriesQuery
import javax.inject.Inject

class GetCountriesUseCase @Inject constructor(
    private val leadsClient: LeadsClient
) {
    suspend fun execute(): List<CountriesQuery.FetchCountry> {
        return leadsClient
            .getCountries()
    }
}