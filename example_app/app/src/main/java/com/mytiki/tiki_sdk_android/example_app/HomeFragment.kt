package com.mytiki.tiki_sdk_android.example_app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.viewModelScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.mytiki.tiki_sdk_android.example_app.databinding.HomeFragmentBinding
import kotlinx.coroutines.launch
import java.util.*
import kotlin.concurrent.schedule

class HomeFragment : Fragment() {

    private val viewModel by activityViewModels<HomeViewModel>()

    private var _binding: HomeFragmentBinding? = null
    private val binding get() = _binding!!

    private var timer: Timer? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if(viewModel.tikiSdk.value == null){
            viewModel.viewModelScope.launch {
                viewModel.loadTikiSdk(requireContext())
                updateBindings()
            }
        }
        _binding = HomeFragmentBinding.inflate(inflater, container, false)
        setClickListeners()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = RequestsAdapter(viewModel)
        binding.requestsView.adapter = adapter
        val linearLayoutManager = LinearLayoutManager(requireContext())
        linearLayoutManager.reverseLayout = true
        binding.requestsView.layoutManager = linearLayoutManager
        viewModel.log.observe(viewLifecycleOwner) {
            adapter.notifyItemInserted(it.size - 1)
        }
    }

    private fun setClickListeners() {
        binding.walletCard.setOnClickListener {
            Navigation.findNavController(binding.root)
                .navigate(R.id.action_try_it_out_to_walletListFragment)
        }
        binding.toggleConsent.setOnClickListener {
            viewModel.viewModelScope.launch {
                viewModel.modifyConsent(!viewModel.toggleStatus.value!!)
            }.invokeOnCompletion {
                updateBindings()
            }
        }
        binding.ownershipCard.setOnClickListener {
            Navigation.findNavController(binding.root)
                .navigate(R.id.action_try_it_out_to_ownershipFragment)
        }
        binding.consenCard.setOnClickListener {
            Navigation.findNavController(binding.root)
                .navigate(R.id.action_try_it_out_to_consentFragment)
        }
        binding.destinationCard.setOnClickListener {
            Navigation.findNavController(binding.root)
                .navigate(R.id.action_try_it_out_to_destinationFragment)
        }
        binding.bodyCard.setOnClickListener {
            Navigation.findNavController(binding.root)
                .navigate(R.id.action_try_it_out_to_bodyFragment)
        }
    }

    override fun onResume() {
        super.onResume()
        if(viewModel.ownership.value != null && viewModel.source != viewModel.ownership.value!!.source){
            viewModel.viewModelScope.launch{
                viewModel.getOrAssignOnwership()
            }.invokeOnCompletion {
                updateBindings()
            }
        }
        updateBindings()
        timer = Timer()
        timer!!.schedule(
            delay = 0L,
            period = viewModel.interval.value!! * 1000L
        ) {
            viewModel.makeRequest()
        }
    }

    private fun updateBindings() {
        binding.bodyTitle.text = viewModel.body.value!!
        binding.destinationTitle.text = viewModel.httpMethod.value + " " + viewModel.url.value
        binding.walletAddress.text = if(viewModel.tikiSdk.value != null) viewModel.tikiSdk.value!!.address else ""
        binding.ownershipAndConsent.visibility = View.VISIBLE
        binding.ownershipId.text = if(viewModel.ownership.value != null) viewModel.ownership.value!!.transactionId else ""
        binding.consentId.text = if(viewModel.consent.value != null) viewModel.consent.value!!.transactionId else ""
        binding.toggleConsent.isChecked = viewModel.toggleStatus.value == true
    }

    override fun onPause() {
        super.onPause()
        timer?.cancel()
    }
}