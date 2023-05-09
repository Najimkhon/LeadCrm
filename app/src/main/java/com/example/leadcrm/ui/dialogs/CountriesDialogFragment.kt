package com.example.leadcrm.ui.dialogs

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.leadcrm.databinding.CountriesBottomSheetDialogBinding
import com.example.leadcrm.ui.adapters.CountryAdapter
import com.example.leadcrm.ui.layouts.CountryItemLayout
import com.example.leadcrm.ui.viewmodels.LeadsViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CountriesDialogFragment(listener: CountryItemLayout.OnItemClickListener) :
    BottomSheetDialogFragment() {
    private var _binding: CountriesBottomSheetDialogBinding? = null
    private val binding get() = _binding!!
    private val viewModel: LeadsViewModel by activityViewModels()
    private val countryAdapter: CountryAdapter by lazy { CountryAdapter(requireContext(), listener) }

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
            Log.d("CountriesDialogFragment", "Received countries: ${it.size}")
            countryAdapter.setData(it)
        }
        viewModel.getCountries()

        setupSearchEditText()

        return binding.root
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

                val query = s.toString().trim()
                Log.d("CountriesDialogFragment", "Search query: $query")
                countryAdapter.filter(query)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}