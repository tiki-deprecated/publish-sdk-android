package com.mytiki.tiki_sdk_android.example_app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.mytiki.tiki_sdk_android.example_app.databinding.OwnershipFragmentBinding

class OwnershipFragment : Fragment() {

    private val viewModel by activityViewModels<HomeViewModel>()

    private var _binding: OwnershipFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = OwnershipFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.hash.text = viewModel.ownership.value!!.transactionId

        binding.source.text = viewModel.ownership.value!!.source

        binding.contains.text = viewModel.ownership.value!!.contains.joinToString(", ")

        binding.about.text = viewModel.ownership.value!!.about

        binding.origin.text = viewModel.ownership.value!!.origin
    }
}