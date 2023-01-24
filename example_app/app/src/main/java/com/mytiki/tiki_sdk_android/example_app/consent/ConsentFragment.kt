package com.mytiki.tiki_sdk_android.example_app.consent

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.mytiki.tiki_sdk_android.example_app.databinding.ConsentFragmentBinding
import com.mytiki.tiki_sdk_android.example_app.databinding.OwnershipFragmentBinding
import com.mytiki.tiki_sdk_android.example_app.try_it_out.TryItOutViewModel
import java.text.DateFormat
import java.util.*

class ConsentFragment : Fragment() {

    private val viewModel by activityViewModels<TryItOutViewModel>()

    private var _binding: ConsentFragmentBinding? = null
    private val binding get() = _binding!!

    private var timer: Timer? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ConsentFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.hash.text = viewModel.consent!!.transactionId
        binding.paths.text = viewModel.consent!!.destination.paths.joinToString(", ")
        binding.uses.text = viewModel.consent!!.destination.uses.joinToString(", ")
        binding.about.text = viewModel.consent!!.about
        binding.reward.text = viewModel.consent!!.reward
        binding.expiry.text = DateFormat.getDateTimeInstance().format(viewModel.consent!!.expiry!!)
    }
}