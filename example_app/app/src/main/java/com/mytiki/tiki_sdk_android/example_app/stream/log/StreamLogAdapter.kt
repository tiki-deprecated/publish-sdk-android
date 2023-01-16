package com.mytiki.tiki_sdk_android.example_app.stream.log

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mytiki.tiki_sdk_android.example_app.databinding.StreamLogItemBinding

class StreamLogAdapter(private val viewModel: StreamLogViewModel) : RecyclerView.Adapter<StreamLogViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StreamLogViewHolder {
        val binding: StreamLogItemBinding = StreamLogItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)
        return StreamLogViewHolder(binding)
    }

    override fun getItemCount() = viewModel.logs.value!!.size

    override fun onBindViewHolder(holder: StreamLogViewHolder, position: Int) {
        val stream = viewModel.logs.value!![position]
        holder.bind(stream)
    }
}
