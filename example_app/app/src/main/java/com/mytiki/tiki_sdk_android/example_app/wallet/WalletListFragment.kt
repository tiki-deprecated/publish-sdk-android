package com.mytiki.tiki_sdk_android.example_app.wallet

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.mytiki.tiki_sdk_android.example_app.databinding.WalletFragmentBinding
import com.mytiki.tiki_sdk_android.example_app.try_it_out.TryItOutViewModel

class WalletListFragment : Fragment() {
    private val viewModel by activityViewModels<TryItOutViewModel>()

    private var _binding: WalletFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = WalletFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)
        val adapter = WalletListAdapter(viewModel)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        viewModel.wallets.observe(viewLifecycleOwner, Observer {
            binding.recyclerView.adapter = adapter
        })
        viewModel.isLoading.observe(viewLifecycleOwner, Observer {
            binding.button.isEnabled = !it
        })
        binding.button.setOnClickListener {
            viewModel.loadTikiSdk(requireContext())
        }
    }
}


