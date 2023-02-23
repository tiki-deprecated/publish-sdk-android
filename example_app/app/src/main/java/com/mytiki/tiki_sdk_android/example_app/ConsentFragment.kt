package com.mytiki.tiki_sdk_android.example_app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.mytiki.tiki_sdk_android.example_app.databinding.ConsentFragmentBinding
import java.text.DateFormat

class ConsentFragment : Fragment() {

    private val viewModel by activityViewModels<HomeViewModel>()

    private var _binding: ConsentFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ConsentFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.hash.text = viewModel.consent.value!!.transactionId
        binding.paths.text = viewModel.consent.value!!.destination.paths.joinToString(", ")
        binding.uses.text = viewModel.consent.value!!.destination.uses.joinToString(", ")
        binding.about.text = viewModel.consent.value!!.about
        binding.reward.text = viewModel.consent.value!!.reward
        binding.expiry.text = if(viewModel.consent.value?.expiry != null)
                DateFormat.getDateTimeInstance().format(viewModel.consent.value!!.expiry!!)
            else
                ""
        }
}