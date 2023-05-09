package com.example.leadcrm.ui.dialogs

import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.graphql.CountriesQuery
import com.example.leadcrm.databinding.CountriesBottomSheetDialogBinding
import com.example.leadcrm.ui.adapters.CountryAdapter
import com.example.leadcrm.ui.layouts.CountryItemLayout
import com.example.leadcrm.ui.viewmodels.LeadsViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.*

class CountriesDialogFragment(listener: CountryItemLayout.OnItemClickListener) :
    BottomSheetDialogFragment() {
    private var _binding: CountriesBottomSheetDialogBinding? = null
    private val binding get() = _binding!!
    private val viewModel: LeadsViewModel by activityViewModels()
    private val countryAdapter: CountryAdapter by lazy {
        CountryAdapter(
            requireContext(),
            listener
        )
    }
    private var originalList = mutableListOf<CountriesQuery.FetchCountry>()
    private val filteredList = MutableLiveData<List<CountriesQuery.FetchCountry>>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = CountriesBottomSheetDialogBinding.inflate(inflater, container, false)

        binding.rvCountries.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = countryAdapter
        }

        viewModel.countriesLiveData.observe(this) {
            originalList.clear()
            originalList.addAll(it)

            filteredList.postValue(originalList)
        }
        viewModel.getCountries()

        setupSearchEditText()

        filteredList.observe(this) {
            countryAdapter.setData(it)
        }

        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)

        return dialog
    }

    fun filter(query: String) {
        if (query.trim().isEmpty()) {
            filteredList.postValue(originalList)
        } else {
            val lowerCaseQuery = query.trim().lowercase(Locale.getDefault())
            val searchResults = mutableListOf<CountriesQuery.FetchCountry>()

            for (country in originalList) {
                if (country.title.lowercase(Locale.getDefault()).startsWith(lowerCaseQuery)) {
                    searchResults.add(country)
                }
            }

            for (country in originalList) {
                if (country.title.lowercase(Locale.getDefault()).contains(lowerCaseQuery) &&
                    !country.title.lowercase(Locale.getDefault()).startsWith(lowerCaseQuery)
                ) {
                    searchResults.add(country)
                }
            }

            filteredList.postValue(searchResults)
        }

    }

    private fun setupSearchEditText() {
        binding.etSearchInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Not used
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Not used
            }

            override fun afterTextChanged(s: Editable?) {
                val query = s.toString()
                filter(query)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}