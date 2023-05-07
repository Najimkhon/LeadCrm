package com.example.leadcrm.ui.fragments

import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.leadcrm.R
import com.example.leadcrm.base.BaseFragment
import com.example.leadcrm.databinding.FragmentLeadListBinding
import com.example.leadcrm.ui.adapters.LeadAdapter
import com.example.leadcrm.ui.layouts.LeadItemLayout
import com.example.leadcrm.ui.viewmodels.LeadsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LeadListFragment : BaseFragment<FragmentLeadListBinding>(FragmentLeadListBinding::inflate), LeadItemLayout.OnClickListener {
    private val viewModel: LeadsViewModel by activityViewModels()
    private val leadAdapter: LeadAdapter by lazy { LeadAdapter(requireContext(), this) }

    override fun setObservers() {
        viewModel.getLeads()
        viewModel.leadsLiveData.observe(viewLifecycleOwner) {
            leadAdapter.setData(it.data)
        }
    }

    override fun setListeners() {
        binding.apply {
            btnAddNewLead.setOnClickListener {
                findNavController().navigate(R.id.navigateToAddLead)
            }
            tvAddNewLead.setOnClickListener {
                findNavController().navigate(R.id.navigateToAddLead)
            }
        }
    }

    override fun prepareUI() {
        binding.rvLeads.apply {
            adapter = leadAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    override fun onItemClicked(leadId: Int) {
        val action = LeadListFragmentDirections.navigateToLeadProfile(leadId)
        findNavController().navigate(action)
    }


}