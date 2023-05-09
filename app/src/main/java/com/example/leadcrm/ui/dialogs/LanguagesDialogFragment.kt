package com.example.leadcrm.ui.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.leadcrm.databinding.LanguagesBottomSheetDialogBinding
import com.example.leadcrm.ui.adapters.LanguageAdapter
import com.example.leadcrm.ui.viewmodels.LeadsViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class LanguagesDialogFragment : BottomSheetDialogFragment() {
    private var _binding: LanguagesBottomSheetDialogBinding? = null
    private val binding get() = _binding!!
    private val viewModel: LeadsViewModel by activityViewModels()
    private val languageAdapter: LanguageAdapter by lazy { LanguageAdapter(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = LanguagesBottomSheetDialogBinding.inflate(inflater, container, false)

        binding.rvLanguages.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = languageAdapter
        }

        viewModel.languagesLiveData.observe(this) {
            languageAdapter.setData(it)
        }
        viewModel.getLanguages()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}