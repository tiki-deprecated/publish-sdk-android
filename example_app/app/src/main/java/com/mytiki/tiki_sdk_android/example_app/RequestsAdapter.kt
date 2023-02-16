package com.mytiki.tiki_sdk_android.example_app

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mytiki.tiki_sdk_android.example_app.databinding.RequestItemBinding

class RequestsAdapter(private val viewModel: HomeViewModel) :
    RecyclerView.Adapter<RequestsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RequestsViewHolder {
        val binding: RequestItemBinding = RequestItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return RequestsViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return viewModel.log.value!!.size
    }

    override fun onBindViewHolder(holder: RequestsViewHolder, position: Int) {
        holder.bind(viewModel.log.value!![position], holder.itemView.context)
    }
}
