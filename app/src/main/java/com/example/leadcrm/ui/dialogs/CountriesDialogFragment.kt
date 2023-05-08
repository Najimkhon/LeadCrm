package com.example.leadcrm.ui.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.leadcrm.databinding.CountriesBottomSheetDialogBinding
import com.example.leadcrm.ui.adapters.CountryAdapter
import com.example.leadcrm.ui.adapters.LeadAdapter
import com.example.leadcrm.ui.viewmodels.LeadsViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CountriesDialogFragment : BottomSheetDialogFragment() {
    private var _binding: CountriesBottomSheetDialogBinding? = null
    private val binding get() = _binding!!
    private val viewModel: LeadsViewModel by activityViewModels()
    private val countryAdapter: CountryAdapter by lazy { CountryAdapter(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = CountriesBottomSheetDialogBinding.inflate(inflater, container, false)

        viewModel.getCountries()
        viewModel.countriesLiveData.observe(this){
            countryAdapter.setData(it)
        }

        binding.rvCountries.apply {
            adapter = countryAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}