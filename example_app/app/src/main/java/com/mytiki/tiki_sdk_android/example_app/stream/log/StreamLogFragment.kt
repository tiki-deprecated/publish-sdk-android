package com.mytiki.tiki_sdk_android.example_app.stream.log

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.mytiki.tiki_sdk_android.TikiSdk
import com.mytiki.tiki_sdk_android.example_app.TikiSdkContainer
import com.mytiki.tiki_sdk_android.example_app.databinding.StreamLogFragmentBinding
import kotlinx.coroutines.launch

class StreamLogFragment : Fragment() {

    private lateinit var viewModel: StreamLogViewModel
    private var _binding: StreamLogFragmentBinding? = null
    private val binding get() = _binding!!
    private var tikiSdk : TikiSdk? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = StreamLogFragmentBinding.inflate(inflater, container, false)
        tikiSdk = TikiSdkContainer.currentSdk
        return binding.root
    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)
        viewModel = ViewModelProvider(this).get(StreamLogViewModel::class.java)
        val adapter = StreamLogAdapter(viewModel)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        viewModel.logs.observe(viewLifecycleOwner, Observer {
            adapter.notifyItemInserted(it.size - 1)
        })
    }
}


