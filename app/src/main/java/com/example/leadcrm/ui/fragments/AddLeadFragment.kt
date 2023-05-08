package com.example.leadcrm.ui.fragments

import com.example.leadcrm.base.BaseFragment
import com.example.leadcrm.databinding.FragmentAddLeadBinding


class AddLeadFragment : BaseFragment<FragmentAddLeadBinding>(FragmentAddLeadBinding::inflate) {

    override fun setListeners() {

        binding.btnSave.setOnClickListener {
            binding.spLeadType.showErrorState()
        }
    }
}