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
import com.example.leadcrm.ui.layouts.LanguageItemLayout
import com.example.leadcrm.ui.viewmodels.LeadsViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.*

class LanguagesDialogFragment(private val listener: OnDialogClosedListener) : BottomSheetDialogFragment(), LanguageItemLayout.OnClickListener {

    private var _binding: LanguagesBottomSheetDialogBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LeadsViewModel by activityViewModels()

    private var originalList = mutableListOf<LanguagesQuery.Language>()
    private val filteredList = MutableLiveData<List<LanguagesQuery.Language>>()
    private val selectionList = mutableListOf<LanguageSelectionHolder>()

    private val languageAdapter: LanguageAdapter by lazy {
        LanguageAdapter(
            requireContext(),
            this,
        )
    }

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
            originalList.clear()
            originalList.addAll(it)

            selectionList.clear()
            originalList.forEach { item ->
                selectionList.add(LanguageSelectionHolder(item.id, false))
            }
            languageAdapter.selectionList = selectionList
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
                filter(query)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        var listOfIds = mutableListOf<Int>()
        selectionList.forEach {
            if (it.isSelected){
                listOfIds.add(it.id)
            }
        }

        listener.onDialogClosed(listOfIds)
        _binding = null
    }

    override fun onItemClicked(languageId: Int, position: Int) {

        val languageSelectionHolder = selectionList.find { it.id == languageId }!!
        languageSelectionHolder.isSelected = !languageSelectionHolder.isSelected

        languageAdapter.notifyItemChanged(position)
    }

    interface OnDialogClosedListener{
        fun onDialogClosed(selectedLanguages: List<Int>)
    }

    data class LanguageSelectionHolder(val id: Int, var isSelected: Boolean)
}