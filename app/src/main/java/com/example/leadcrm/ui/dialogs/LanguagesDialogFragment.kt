package com.example.leadcrm.ui.dialogs

import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.graphql.LanguagesQuery
import com.example.leadcrm.databinding.LanguagesBottomSheetDialogBinding
import com.example.leadcrm.ui.adapters.LanguageAdapter
import com.example.leadcrm.ui.viewmodels.LeadsViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.*

class LanguagesDialogFragment : BottomSheetDialogFragment() {
    private var _binding: LanguagesBottomSheetDialogBinding? = null
    private val binding get() = _binding!!
    private val viewModel: LeadsViewModel by activityViewModels()
    private val languageAdapter: LanguageAdapter by lazy { LanguageAdapter(requireContext()) }
    private var originalList = mutableListOf<LanguagesQuery.Language>()
    private val filteredList = MutableLiveData<List<LanguagesQuery.Language>>()

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
            originalList.clear() // Clear the list before re-assigning
            originalList.addAll(it)
            filteredList.postValue(originalList)
        }
        viewModel.getLanguages()

        setupSearchEditText()
        filteredList.observe(this) {
            languageAdapter.setData(it)
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
            val searchResults = mutableListOf<LanguagesQuery.Language>()

            for (language in originalList) {
                if (language.title.lowercase(Locale.getDefault()).startsWith(lowerCaseQuery)) {
                    searchResults.add(language)
                }
            }

            for (language in originalList) {
                if (language.title.lowercase(Locale.getDefault()).contains(lowerCaseQuery) &&
                    !language.title.lowercase(Locale.getDefault()).startsWith(lowerCaseQuery)
                ) {
                    searchResults.add(language)
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
                Log.d("CountriesDialogFragment", "Search query: $query")
                filter(query)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}