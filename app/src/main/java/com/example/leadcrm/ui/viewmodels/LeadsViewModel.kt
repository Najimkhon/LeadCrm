package com.example.leadcrm.ui.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.graphql.*
import com.example.graphql.type.CreateLeadInput
import com.example.graphql.type.FetchLeadInput
import com.example.graphql.type.UpdateLeadInput
import com.example.leadcrm.domain.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LeadsViewModel @Inject constructor(
    private val getLeadsUseCase: GetLeadsUseCase,
    private val getCountriesUseCase: GetCountriesUseCase,
    private val getStatusListUseCase: GetStatusListUseCase,
    private val getLanguagesUseCase: GetLanguagesUseCase,
    private val createLeadUseCase: CreateLeadUseCase,
    private val getLeadUseCase: FetchLeadUseCase,
    private val updateLeadUseCase: UpdateLeadUseCase
) : ViewModel() {

    private val _leadsLiveData = MutableLiveData<LeadsQuery.FetchLeads>()
    val leadsLiveData = _leadsLiveData

    private val _countriesLiveData = MutableLiveData<List<CountriesQuery.FetchCountry>>()
    val countriesLiveData = _countriesLiveData

    private val _statusListLiveData = MutableLiveData<List<StatusQuery.FetchLeadStatusType>>()
    val statusListLiveData = _statusListLiveData

    private val _languagesLiveData = MutableLiveData<List<LanguagesQuery.Language>>()
    val languagesLiveData = _languagesLiveData

    private val _getLeadLiveData = MutableLiveData<FetchLeadQuery.FetchLead?>()
    val getLeadLiveData = _getLeadLiveData


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
    fun getLanguages(){
        viewModelScope.launch {
            try {
                val response = getLanguagesUseCase.execute()
                _languagesLiveData.postValue(response)
                Log.d("GetLanguages", "Success $response")
            } catch (e: Exception) {
                Log.e("GetLanguages", "Error: ${e.message}", e)
            }
        }
    }

    fun createLead(newLead: CreateLeadInput){
        viewModelScope.launch {
            try {
                val response = createLeadUseCase.execute(newLead)
                Log.d("CreateLead", "Success $response")
            } catch (e: Exception) {
                Log.e("CreateLead", "Error: ${e.message}", e)
            }
        }
    }

    fun getLead(lead: FetchLeadInput){
        viewModelScope.launch {
            try {
                val response = getLeadUseCase.execute(lead)
                _getLeadLiveData.postValue(response)
                Log.d("GetLead", "Success $response")
            } catch (e: Exception) {
                Log.e("GetLead", "Error: ${e.message}", e)
            }
        }
    }

    fun updateLead(updatedLead: UpdateLeadInput){
        viewModelScope.launch {
            try {
                val response = updateLeadUseCase.execute(updatedLead)
                Log.d("UpdateLead", "Success $response")
            } catch (e: Exception) {
                Log.e("UpdateLead", "Error: ${e.message}", e)
            }
        }
    }

}