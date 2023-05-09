package com.example.leadcrm.ui.fragments

import androidx.core.view.isVisible
import com.example.leadcrm.base.BaseFragment
import com.example.leadcrm.databinding.FragmentLeadProfileBinding

class LeadProfileFragment :
    BaseFragment<FragmentLeadProfileBinding>(FragmentLeadProfileBinding::inflate) {

    override fun setListeners() {
        binding.generaInfoForm.btnEditGeneralInfo.setOnClickListener{
            isEditableMode(true)
        }
        binding.generaInfoForm.btnSaveGeneralInfo.setOnClickListener{
            isEditableMode(false)
        }
    }

    override fun prepareUI() {
        binding.generaInfoForm.apply {
            etCountry.isEditable(false)
            etLanguage.isEditable(false)
        }
        isEditableMode(false)

    }

    private fun isEditableMode(isEditable: Boolean) {
        binding.apply {
            generaInfoForm.apply {
                etAdSource.isEditable(isEditable)
                etBirthday.isEditable(isEditable)
                etBudget.isEditable(isEditable)
                etChannel.isEditable(isEditable)
                etCity.isEditable(isEditable)
                etLeadType.isEditable(isEditable)
                etWebSource.isEditable(isEditable)
                etProperty.isEditable(isEditable)
                etNationality.isEditable(isEditable)
                etBirthday.isEditable(isEditable)
                etBudget.isEditable(isEditable)
                etCityProperty.isEditable(isEditable)
                etChannelProperty.isEditable(isEditable)
                etLanguageProperty.isEditable(isEditable)
                btnEditGeneralInfo.isVisible = !isEditable
                btnSaveGeneralInfo.isVisible = isEditable
            }
        }
    }

}