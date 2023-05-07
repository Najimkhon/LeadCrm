package com.example.leadcrm.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.graphql.LeadsQuery
import com.example.leadcrm.domain.GetLeadsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LeadsViewModel @Inject constructor(
    private val getLeadsUseCase: GetLeadsUseCase
): ViewModel() {

    private val _leadsLiveData = MutableLiveData<LeadsQuery.FetchLeads>()
    val leadsLiveData = _leadsLiveData

    fun getLeads(){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = getLeadsUseCase.execute()
                _leadsLiveData.postValue(response)
                Log.d("GetLeads", "Success ${response.data}")
            }catch (e: Exception) {
                Log.e("GetLeads", "Error: ${e.message}", e)
            }
        }
    }
}