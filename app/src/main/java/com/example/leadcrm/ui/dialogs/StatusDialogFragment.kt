package com.example.leadcrm.ui.dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.leadcrm.databinding.StatusBottomSheetDialogBinding
import com.example.leadcrm.ui.adapters.StatusAdapter
import com.example.leadcrm.ui.layouts.StatusItemLayout
import com.example.leadcrm.ui.viewmodels.LeadsViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class StatusDialogFragment(private val listener: StatusItemLayout.OnStatusSelectedListener) : BottomSheetDialogFragment() {
    private var _binding: StatusBottomSheetDialogBinding? = null
    private val binding get() = _binding!!
    private val viewModel: LeadsViewModel by activityViewModels()
    private val statusAdapter: StatusAdapter by lazy { StatusAdapter(requireContext(), listener) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = StatusBottomSheetDialogBinding.inflate(inflater, container, false)

        binding.rvCountries.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = statusAdapter
        }

        viewModel.statusListLiveData.observe(this) {
            statusAdapter.setData(it)
        }

        viewModel.getStatusList()

        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)

        return dialog
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}