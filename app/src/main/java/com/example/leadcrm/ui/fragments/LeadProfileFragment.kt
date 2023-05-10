package com.example.leadcrm.ui.fragments

import android.graphics.Color
import android.view.View
import android.widget.LinearLayout
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.graphql.type.FetchLeadInput
import com.example.leadcrm.R
import com.example.leadcrm.base.BaseFragment
import com.example.leadcrm.databinding.FragmentLeadProfileBinding
import com.example.leadcrm.ui.viewmodels.LeadsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LeadProfileFragment :
    BaseFragment<FragmentLeadProfileBinding>(FragmentLeadProfileBinding::inflate) {
    private val args: LeadProfileFragmentArgs by navArgs()
    private val viewModel: LeadsViewModel by viewModels()

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
        }

        binding.btnBack.setOnClickListener{
            findNavController().popBackStack()
        }
    }

    override fun prepareUI() {
        viewModel.getLead(FetchLeadInput(args.leadId))
        viewModel.getLeadLiveData.observe(this) {
            binding.apply {
                val name = it?.data?.firstName
                val lastName = it?.data?.lastName
                etName.setText(name)
                etLastName.setText(lastName)
                tvId.text = "ID: ${it?.data?.id}"
                tvInitials.text = name?.get(0).toString() + lastName?.get(0).toString()
            }
            binding.progressBar.apply {
                tvOptionsLabel.text = it?.data?.status?.title
                setProgressGraph(it?.data?.status?.step, it?.data?.status?.stepsCount)
                cvStatusColor.setCardBackgroundColor(Color.parseColor(it?.data?.status?.color))
            }
        }

        binding.generaInfoForm.apply {
            etCountry.isEditable(false)
            etLanguage.isEditable(false)
            viewModel.getLeadLiveData.observe(viewLifecycleOwner) {
                val lead = it?.data
                etLanguage.setText(lead?.languages?.get(0)?.title.toString())
                etAdSource.setText(lead?.adSource?.title ?: "")
                etWebSource.setText(lead?.webSource?.title ?: "")
                etCity.setText(lead?.city?.title ?: "")
                etChannel.setText(lead?.channelSource?.title ?: "")
                etProperty.setText(lead?.propertyType?.title ?: "")
                etNationality.setText(lead?.nationality?.title ?: "")
            }
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

    private fun updateLead(){

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
}