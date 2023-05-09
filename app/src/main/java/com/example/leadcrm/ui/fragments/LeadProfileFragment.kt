package com.example.leadcrm.ui.fragments

import com.example.leadcrm.base.BaseFragment
import com.example.leadcrm.databinding.FragmentLeadProfileBinding

class LeadProfileFragment : BaseFragment<FragmentLeadProfileBinding>(FragmentLeadProfileBinding::inflate) {

    override fun prepareUI() {
        binding.generaInfoForm.etLeadType.isEnabled = false
    }
}