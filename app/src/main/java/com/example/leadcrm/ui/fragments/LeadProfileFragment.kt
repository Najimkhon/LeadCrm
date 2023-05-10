package com.example.leadcrm.ui.fragments

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.apollographql.apollo3.api.Optional
import com.example.graphql.type.FetchLeadInput
import com.example.graphql.type.UpdateLeadInput
import com.example.leadcrm.R
import com.example.leadcrm.base.BaseFragment
import com.example.leadcrm.databinding.FragmentLeadProfileBinding
import com.example.leadcrm.ui.dialogs.CountriesDialogFragment
import com.example.leadcrm.ui.dialogs.LanguagesDialogFragment
import com.example.leadcrm.ui.layouts.CountryItemLayout
import com.example.leadcrm.ui.viewmodels.LeadsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LeadProfileFragment :
    BaseFragment<FragmentLeadProfileBinding>(FragmentLeadProfileBinding::inflate),
    CountryItemLayout.OnItemClickListener, LanguagesDialogFragment.OnDialogClosedListener  {
    private val args: LeadProfileFragmentArgs by navArgs()
    private val viewModel: LeadsViewModel by viewModels()
    private val countriesDialog = CountriesDialogFragment(this)
    private var selectedCountryId = -1
    private var selectedLanguages = mutableListOf<Int>()
    private val languagesDialog = LanguagesDialogFragment(this)

    override fun setListeners() {
        binding.generaInfoForm.btnEditGeneralInfo.setOnClickListener {
            isEditableMode(true)
        }
        binding.generaInfoForm.btnSaveGeneralInfo.setOnClickListener {
            isEditableMode(false)
        }

        binding.btnEditName.setOnClickListener {
            isNameEditable(true)
        }

        binding.btnSaveName.setOnClickListener {
            isNameEditable(false)
            updateLead(args.leadId)
        }

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.generaInfoForm.etCountry.setOnClickListener{
            countriesDialog.show(childFragmentManager, "CountriesDialog")
        }

        binding.generaInfoForm.etLanguage.setOnClickListener{
            languagesDialog.show(childFragmentManager, "LanguagesDialog")
        }
    }

    override fun prepareUI() {
        viewModel.getLead(FetchLeadInput(args.leadId))

        viewModel.getLeadLiveData.observe(viewLifecycleOwner) {

            binding.progressBar.apply {
                tvOptionsLabel.text = it?.data?.status?.title
                setProgressGraph(it?.data?.status?.step, it?.data?.status?.stepsCount)
                cvStatusColor.setCardBackgroundColor(Color.parseColor(it?.data?.status?.color))
            }

            binding.apply {
                val name = it?.data?.firstName
                val lastName = it?.data?.lastName
                etName.setText(name)
                etLastName.setText(lastName)
                tvId.text = "ID: ${it?.data?.id}"
                tvInitials.text = name?.get(0).toString() + lastName?.get(0).toString()
            }

            binding.generaInfoForm.apply {
                val lead = it?.data
                lead?.languages?.forEach {
                    etLanguage.setText(it.title)
                }

                etAdSource.setText(lead?.adSource?.title ?: "")
                etWebSource.setText(lead?.webSource?.title ?: "")
                etCity.setText(lead?.city?.title ?: "")
                etChannel.setText(lead?.channelSource?.title ?: "")
                etProperty.setText(lead?.propertyType?.title ?: "")
                etNationality.setText(lead?.nationality?.title ?: "")
                val country = lead?.country
                if (country!= null){
                    etCountry.setText(country.title)
                    selectedCountryId = it.data.country?.id ?: -1
                }
                lead?.languages?.forEach {
                    etLanguage.setText(it.title)
                    selectedLanguages.add(it.id)
                }
            }

            binding.buttonsChain.apply {
                val contacts = it?.data?.contacts?.data
                contacts?.forEach {
                    if (it.emailContact?.title != null){
                        btnMail.setBackgroundDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.icon_purple_bg))
                        tvMailLabel.setTextColor(ContextCompat.getColor(requireContext(), R.color.blue))
                        setMailListener(it.emailContact.title)
                    }else{
                        btnMail.setBackgroundDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.icon_purple_disabled_bg))
                        tvMailLabel.setTextColor(ContextCompat.getColor(requireContext(), R.color.disabled_button_icon_color))
                    }
                    if (it.phoneContact?.title != null){
                        btnCall.setBackgroundDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.icon_purple_bg))
                        tvCallLabel.setTextColor(ContextCompat.getColor(requireContext(), R.color.blue))
                        btnMessage.setBackgroundDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.icon_purple_bg))
                        tvMessageLabel.setTextColor(ContextCompat.getColor(requireContext(), R.color.blue))
                        setPhoneListener(it.phoneContact.title)
                    }else{
                        btnCall.setBackgroundDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.icon_purple_disabled_bg))
                        tvCallLabel.setTextColor(ContextCompat.getColor(requireContext(), R.color.disabled_button_icon_color))
                        btnMessage.setBackgroundDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.icon_purple_disabled_bg))
                        tvMessageLabel.setTextColor(ContextCompat.getColor(requireContext(), R.color.disabled_button_icon_color))
                    }
                }
            }
        }

        isEditableMode(false)
    }

    private fun setMailListener(email: String){
        binding.buttonsChain.btnMail.setOnClickListener{
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:$email")
            }
            if (intent.resolveActivity(requireActivity().packageManager) != null) {
                startActivity(intent)
            } else {
                Toast.makeText(requireContext(), "No email app found", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setPhoneListener(phoneNumber: String){
        binding.buttonsChain.btnCall.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:$phoneNumber")
            }

            if (intent.resolveActivity(requireActivity().packageManager) != null) {
                startActivity(intent)
            } else {
                Toast.makeText(requireContext(), "No phone app found", Toast.LENGTH_SHORT).show()
            }
        }

        binding.buttonsChain.btnMessage.setOnClickListener{

            val smsIntent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("smsto:$phoneNumber")
            }
            if (smsIntent.resolveActivity(requireActivity().packageManager) != null) {
                startActivity(smsIntent)
            } else {
                Toast.makeText(requireContext(), "No SMS app found", Toast.LENGTH_SHORT).show()
            }
        }
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

    private fun isNameEditable(isEditable: Boolean) {
        binding.apply {
            etName.isEnabled = isEditable
            etLastName.isEnabled = isEditable
            btnEditName.isVisible = !isEditable
            btnSaveName.isVisible = isEditable
        }
    }

    private fun dpToPx(dp: Int): Int {
        val scale = resources.displayMetrics.density
        return (dp * scale + 0.5f).toInt()
    }

    private fun updateLead(leadId: Int) {
        val updateLeadInput = UpdateLeadInput(
            leadId = leadId,
            firstName = Optional.Present(binding.etName.text.toString()),
            lastName = Optional.Present(binding.etLastName.text.toString()),
            countryId = Optional.Present(selectedCountryId),
            languageIds = Optional.Present(selectedLanguages)
        )
        viewModel.updateLead(updateLeadInput)
        viewModel.getLead(FetchLeadInput(args.leadId))
    }

    private fun setProgressGraph(completedSteps: Int?, totalSteps: Int?) {
        binding.progressBar.progressBlocksContainer.removeAllViews()
        if (completedSteps != null && totalSteps != null) {
            for (i in 0 until totalSteps) {
                val stepView = View(requireContext())
                val params = LinearLayout.LayoutParams(0, dpToPx(9))
                params.weight = 1f
                params.setMargins(0, 0, 4, 0)
                stepView.layoutParams = params

                if (i < completedSteps) {
                    stepView.setBackgroundResource(R.drawable.completed_step)
                    stepView.elevation = 4f
                } else {
                    stepView.setBackgroundResource(R.drawable.step)
                }
                binding.progressBar.progressBlocksContainer.addView(stepView)
            }
        }
    }

    override fun onItemClicked(countryId: Int, countryTitle: String) {
        selectedCountryId = countryId
        binding.generaInfoForm.etCountry.setSelected()
        binding.generaInfoForm.etCountry.setText(countryTitle)
        countriesDialog.dismiss()
    }

    override fun onDialogClosed(selectedLanguages: List<Int>) {
        this.selectedLanguages.clear()
        this.selectedLanguages.addAll(selectedLanguages)
        if (this.selectedLanguages.size > 0) {
            binding.generaInfoForm.etLanguage.setText("Selected")
            binding.generaInfoForm.etLanguage.setSelected()
        } else {
            binding.generaInfoForm.etLanguage.setText("Language")
            binding.generaInfoForm.etLanguage.setDeselected()
        }
    }
}