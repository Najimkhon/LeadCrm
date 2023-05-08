package com.example.leadcrm.ui.fragments

import com.example.leadcrm.base.BaseFragment
import com.example.leadcrm.databinding.FragmentAddLeadBinding
import com.example.leadcrm.ui.dialogs.CountriesDialogFragment


class AddLeadFragment : BaseFragment<FragmentAddLeadBinding>(FragmentAddLeadBinding::inflate) {

    override fun setListeners() {
        val countriesDialog = CountriesDialogFragment()

        binding.spCountry.setOnClickListener{
            countriesDialog.show(childFragmentManager, "MyBottomSheetDialog")
        }

        binding.btnSave.setOnClickListener {
            binding.spLeadType.showErrorState()
        }
    }
}