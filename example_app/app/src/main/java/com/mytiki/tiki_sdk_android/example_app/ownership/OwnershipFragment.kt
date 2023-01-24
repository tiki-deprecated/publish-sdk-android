package com.mytiki.tiki_sdk_android.example_app.ownership

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.mytiki.tiki_sdk_android.example_app.databinding.OwnershipFragmentBinding
import com.mytiki.tiki_sdk_android.example_app.try_it_out.TryItOutViewModel
import java.util.*

class OwnershipFragment : Fragment() {

    private val viewModel by activityViewModels<TryItOutViewModel>()

    private var _binding: OwnershipFragmentBinding? = null
    private val binding get() = _binding!!

    private var timer: Timer? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = OwnershipFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.hash.text = viewModel.ownership!!.transactionId

        binding.source.text = viewModel.ownership!!.source

        binding.contains.text = viewModel.ownership!!.contains.joinToString(", ")

        binding.about.text = viewModel.ownership!!.about

        binding.origin.text = viewModel.ownership!!.origin
    }
}