package com.example.leadcrm.ui.fragments

import com.example.leadcrm.base.BaseFragment
import com.example.leadcrm.databinding.FragmentAddLeadBinding
import com.example.leadcrm.ui.adapters.Topic
import com.example.leadcrm.ui.adapters.TopicSpinnerAdapter


class AddLeadFragment : BaseFragment<FragmentAddLeadBinding>(FragmentAddLeadBinding::inflate) {

    private lateinit var topicSpinnerAdapter: TopicSpinnerAdapter


    override fun assignObjects() {
        topicSpinnerAdapter = TopicSpinnerAdapter(requireContext())
        binding.spLeadType.setAdapter(topicSpinnerAdapter)
        binding.spCountry.setAdapter(topicSpinnerAdapter)
        binding.spCity.setAdapter(topicSpinnerAdapter)
        binding.spLanguage.setAdapter(topicSpinnerAdapter)
        binding.spSource.setAdapter(topicSpinnerAdapter)
    }

    override fun setListeners() {

        binding.btnSave.setOnClickListener {
            binding.spLeadType.showErrorState()
        }
    }

    override fun prepareUI() {
        val item1 = Topic(
            0,
            "Item1",
            1
        )
        val item2 = Topic(
            1,
            "Item2",
            1
        )
        val item3 = Topic(
            2,
            "Item3",
            1
        )

        val list = mutableListOf<Topic>()
        list.add(item1)
        list.add(item2)
        list.add(item3)

        topicSpinnerAdapter.updateList(list)
    }

}