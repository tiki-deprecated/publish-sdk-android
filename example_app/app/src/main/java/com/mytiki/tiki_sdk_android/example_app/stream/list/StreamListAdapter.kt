package com.mytiki.tiki_sdk_android.example_app.stream.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mytiki.tiki_sdk_android.example_app.databinding.StreamListItemBinding

class StreamListAdapter(private val viewModel: StreamListViewModel) : RecyclerView.Adapter<StreamListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StreamListViewHolder {
        val binding: StreamListItemBinding = StreamListItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)
        return StreamListViewHolder(binding)
    }

    override fun getItemCount() = viewModel.streams.value!!.size

    override fun onBindViewHolder(holder: StreamListViewHolder, position: Int) {
        val stream = viewModel.streams.value!![position]
//        holder.bind(stream)
    }
}
