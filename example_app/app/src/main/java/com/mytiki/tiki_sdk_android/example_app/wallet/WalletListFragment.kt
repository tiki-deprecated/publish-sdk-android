package com.mytiki.tiki_sdk_android.example_app.wallet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
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

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)
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
            viewModel.loadTikiSdk(requireContext())
        }
    }
}


