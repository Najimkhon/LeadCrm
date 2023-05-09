package com.example.leadcrm.ui.fragments

import com.example.leadcrm.base.BaseFragment
import com.example.leadcrm.databinding.FragmentAddLeadBinding
import com.example.leadcrm.ui.dialogs.CountriesDialogFragment
import com.example.leadcrm.ui.dialogs.StatusDialogFragment


class AddLeadFragment : BaseFragment<FragmentAddLeadBinding>(FragmentAddLeadBinding::inflate) {

    override fun setListeners() {
        val countriesDialog = CountriesDialogFragment()
        val statusDialog = StatusDialogFragment()

        binding.spCountry.setOnClickListener{
            countriesDialog.show(childFragmentManager, "CountriesDialog")
        }

        binding.btnSave.setOnClickListener {
            binding.spLeadType.showErrorState()
        }

        binding.spLeadType.setOnClickListener{
            statusDialog.show(childFragmentManager, "StatusDialog")
        }
    }
}