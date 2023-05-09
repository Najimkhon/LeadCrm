package com.example.leadcrm.ui.fragments

import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.apollographql.apollo3.api.Optional
import com.example.graphql.type.ContactDataInput
import com.example.graphql.type.CreateLeadInput
import com.example.leadcrm.base.BaseFragment
import com.example.leadcrm.databinding.FragmentAddLeadBinding
import com.example.leadcrm.ui.dialogs.CountriesDialogFragment
import com.example.leadcrm.ui.dialogs.LanguagesDialogFragment
import com.example.leadcrm.ui.layouts.CountryItemLayout
import com.example.leadcrm.ui.viewmodels.LeadsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddLeadFragment : BaseFragment<FragmentAddLeadBinding>(FragmentAddLeadBinding::inflate),
    LanguagesDialogFragment.OnDialogClosedListener, CountryItemLayout.OnItemClickListener {

    private val viewModel: LeadsViewModel by activityViewModels()
    private lateinit var newLead: CreateLeadInput
    private var selectedLanguages = mutableListOf<Int>()
    private var selectedCountryId = -1
    private val countriesDialog = CountriesDialogFragment(this)

    override fun setListeners() {

        val languagesDialog = LanguagesDialogFragment(this)

        binding.spCountry.setOnClickListener {
            countriesDialog.show(childFragmentManager, "CountriesDialog")
        }

        binding.btnSave.setOnClickListener {
            saveNewLead()
            Toast.makeText(requireContext(), "${binding.etFirstName}", Toast.LENGTH_SHORT).show()
        }

        binding.spLanguage.setOnClickListener {
            languagesDialog.show(childFragmentManager, "LanguagesDialog")
        }
    }

    fun saveNewLead() {
        if (validation()) {
            val phoneNumber = ContactDataInput(
                phone = Optional.Present(binding.etNumber.getText())
            )

            binding.apply {
                newLead = CreateLeadInput(
                    firstName = etFirstName.getText(),
                    intentionId = 1,
                    languageIds = selectedLanguages,
                    contacts = listOf(phoneNumber)
                )
            }
            println("THE NEW LEAD: " + newLead)
            viewModel.createLead(newLead)
            showState()
        } else {
            showState()
        }
    }

    private fun validation(): Boolean {
        return binding.etFirstName.getText().isNotEmpty() && binding.etNumber.getText()
            .isNotEmpty() && binding.spLanguage.getText().isNotEmpty()

    }

    private fun showState() {
        binding.apply {
            if (spLanguage.getText() == "Language") {
                spLanguage.showErrorState()
            } else {
                spLanguage.showNormalState()
            }
            if (etFirstName.getText().isEmpty()) {
                etFirstName.showErrorState()
            } else {
                etFirstName.showNormalState()
            }
            if (etNumber.getText().isEmpty()) {
                etNumber.showErrorState()
            } else {
                etNumber.showNormalState()
            }
        }
    }

    override fun onDialogClosed(selectedLanguages: List<Int>) {
        this.selectedLanguages.clear()
        this.selectedLanguages.addAll(selectedLanguages)
        if (this.selectedLanguages.size > 0) {
            binding.spLanguage.setText("Selected")
            binding.spLanguage.setSelected()
        } else {
            binding.spLanguage.setText("Language")
            binding.spLanguage.setDeselected()
        }
    }

    override fun onItemClicked(countryId: Int, countryTitle: String) {
        selectedCountryId = countryId
        binding.spCountry.setSelected()
        binding.spCountry.setText(countryTitle)
        countriesDialog.dismiss()
    }
}