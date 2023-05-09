package com.example.leadcrm.ui.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.graphql.CountriesQuery
import com.example.graphql.LeadsQuery
import com.example.graphql.StatusQuery
import com.example.leadcrm.domain.GetCountriesUseCase
import com.example.leadcrm.domain.GetLeadsUseCase
import com.example.leadcrm.domain.GetStatusListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LeadsViewModel @Inject constructor(
    private val getLeadsUseCase: GetLeadsUseCase,
    private val getCountriesUseCase: GetCountriesUseCase,
    private val getStatusListUseCase: GetStatusListUseCase
) : ViewModel() {

    private val _leadsLiveData = MutableLiveData<LeadsQuery.FetchLeads>()
    val leadsLiveData = _leadsLiveData

    private val _countriesLiveData = MutableLiveData<List<CountriesQuery.FetchCountry>>()
    val countriesLiveData = _countriesLiveData

    private val _statusListLiveData = MutableLiveData<List<StatusQuery.FetchLeadStatusType>>()
    val statusListLiveData = _statusListLiveData

    fun getLeads() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = getLeadsUseCase.execute()
                _leadsLiveData.postValue(response)
                Log.d("GetLeads", "Success ${response.data}")
            } catch (e: Exception) {
                Log.e("GetLeads", "Error: ${e.message}", e)
            }
        }
    }

    fun getCountries() {
        viewModelScope.launch {
            try {
                val response = getCountriesUseCase.execute()
                _countriesLiveData.postValue(response)
                Log.d("GetCountries", "Success $response")
            } catch (e: Exception) {
                Log.e("GetCountries", "Error: ${e.message}", e)
            }
        }
    }

    fun getStatusList(){
        viewModelScope.launch {
            try {
                val response = getStatusListUseCase.execute()
                _statusListLiveData.postValue(response)
                Log.d("GetStatusList", "Success $response")
            } catch (e: Exception) {
                Log.e("GetStatusList", "Error: ${e.message}", e)
            }
        }
    }
}