package com.example.leadcrm.ui.fragments

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.apollographql.apollo3.ApolloClient
import com.example.graphql.LeadsQuery
import com.example.leadcrm.base.BaseFragment
import com.example.leadcrm.databinding.FragmentLeadListBinding
import com.example.leadcrm.ui.viewmodels.LeadsViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LeadListFragment : BaseFragment<FragmentLeadListBinding>(FragmentLeadListBinding::inflate){
    private val viewModel: LeadsViewModel by activityViewModels()

    override fun setListeners() {
        viewModel.getLeads()
        viewModel.leadsLiveData.observe(viewLifecycleOwner){
          if ( it.data.isEmpty()) {
              println("ITS EMPTYYYYYY!!!!")
          }else{
              println("ITS NOT EMPTYYYYYY!!!!")
          }
            it.data.forEach{
               println("It WORKS!!!  " + it.firstName)
            }
        }
    }
}