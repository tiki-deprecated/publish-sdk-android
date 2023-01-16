package com.mytiki.tiki_sdk_android.example_app.wallet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.mytiki.tiki_sdk_android.example_app.databinding.WalletFragmentBinding

class WalletListFragment : Fragment() {
    private lateinit var viewModel: WalletListViewModel

    private var _binding: WalletFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = WalletFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)
        viewModel = ViewModelProvider(this).get(WalletListViewModel::class.java)
        val adapter = WalletListAdapter(viewModel)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        viewModel.wallets.observe(viewLifecycleOwner, Observer {
            adapter.notifyItemInserted(it.size - 1)
        })
        viewModel.isLoading.observe(viewLifecycleOwner, Observer {
            binding.button.isEnabled = !it
        })
        binding.button.setOnClickListener {
            viewModel.createWallet(requireContext())
        }
    }
}


