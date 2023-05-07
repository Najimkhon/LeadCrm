package com.example.leadcrm.ui.fragments

import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.leadcrm.base.BaseFragment
import com.example.leadcrm.databinding.FragmentLeadListBinding
import com.example.leadcrm.ui.adapters.LeadAdapter
import com.example.leadcrm.ui.viewmodels.LeadsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LeadListFragment : BaseFragment<FragmentLeadListBinding>(FragmentLeadListBinding::inflate) {
    private val viewModel: LeadsViewModel by activityViewModels()
    private val leadAdapter: LeadAdapter by lazy { LeadAdapter(requireContext()) }

    override fun setListeners() {
        viewModel.getLeads()
        viewModel.leadsLiveData.observe(viewLifecycleOwner) {
            leadAdapter.setData(it.data)
        }
        binding.rvLeads.apply {
            adapter = leadAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }
}