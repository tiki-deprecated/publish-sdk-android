package com.mytiki.tiki_sdk_android.example_app.stream.list

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.mytiki.tiki_sdk_android.TikiSdk
import com.mytiki.tiki_sdk_android.example_app.TikiSdkContainer
import com.mytiki.tiki_sdk_android.example_app.databinding.StreamListFragmentBinding
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class StreamListFragment : Fragment() {

    private lateinit var viewModel: StreamListViewModel
    private var _binding: StreamListFragmentBinding? = null
    private val binding get() = _binding!!
    private var tikiSdk : TikiSdk? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = StreamListFragmentBinding.inflate(inflater, container, false)
        tikiSdk = TikiSdkContainer.currentSdk
        return binding.root
    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)
        viewModel = ViewModelProvider(this).get(StreamListViewModel::class.java)
        val adapter = StreamListAdapter(viewModel)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        viewModel.streams.observe(viewLifecycleOwner, Observer {
            adapter.notifyItemInserted(it.size - 1)
        })
        viewModel.isLoading.observe(viewLifecycleOwner, Observer {
            binding.newStreamButton.isEnabled = !it
        })
        binding.newStreamButton.setOnClickListener {
            val dataToSend: String = binding.data.text.toString()
            if (tikiSdk == null) {
                Toast.makeText(requireContext(), "Loading TIKI SDK...", Toast.LENGTH_SHORT)
                    .show()
            } else if (dataToSend.isNotEmpty()) {
                viewModel.createStream(tikiSdk, dataToSend)
            } else {
                Toast.makeText(requireContext(), "Type a data to send", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}


