package com.mytiki.tiki_sdk_android.example_app.try_it_out

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.mytiki.tiki_sdk_android.example_app.R
import com.mytiki.tiki_sdk_android.example_app.databinding.TryItOutFragmentBinding
import com.mytiki.tiki_sdk_android.example_app.wallet.TryItOutAdapter
import java.util.*
import kotlin.concurrent.schedule

class TryItOutFragment : Fragment() {

    private val viewModel by activityViewModels<TryItOutViewModel>()

    private var _binding: TryItOutFragmentBinding? = null
    private val binding get() = _binding!!

    private var timer: Timer? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = TryItOutFragmentBinding.inflate(inflater, container, false)
        setClickListeners()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = TryItOutAdapter(viewModel)
        binding.requestsView.adapter = adapter
        binding.requestsView.layoutManager = LinearLayoutManager(requireContext())
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
            viewModel.toggleConsent()
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
        val stream = viewModel.stream.value!!
        binding.destinationTitle.text = stream.httpMethod + " " + stream.url
        binding.bodyTitle.text = stream.body
        if (viewModel.selectedWalletAddress.value != null) {
            binding.walletAddress.text = viewModel.selectedWalletAddress.value
            binding.ownershipAndConsent.visibility = View.VISIBLE
            binding.ownershipId.text = viewModel.ownership!!.transactionId
            binding.consentId.text = viewModel.consent?.transactionId
            binding.toggleConsent.isChecked = viewModel.isConsentGiven.value == true
            timer = Timer()
            timer!!.schedule(
                delay = 0L,
                period = stream.interval * 1000L
            ) {
                viewModel.makeRequest()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        timer?.cancel()
    }
}