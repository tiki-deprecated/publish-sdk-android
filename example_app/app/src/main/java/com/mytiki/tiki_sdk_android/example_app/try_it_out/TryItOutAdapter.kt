package com.mytiki.tiki_sdk_android.example_app.wallet

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mytiki.tiki_sdk_android.example_app.databinding.RequestItemBinding
import com.mytiki.tiki_sdk_android.example_app.databinding.WalletItemBinding
import com.mytiki.tiki_sdk_android.example_app.try_it_out.TryItOutViewModel

class TryItOutAdapter(private val viewModel: TryItOutViewModel) : RecyclerView.Adapter<TryItOutViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TryItOutViewHolder {
        val binding: RequestItemBinding = RequestItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)
        return TryItOutViewHolder(binding)
    }

    override fun getItemCount() : Int {
        val size = viewModel.requests.value!!.size
        return size
    }

    override fun onBindViewHolder(holder: TryItOutViewHolder, position: Int) {
        val req = viewModel.requests.value!![position].req
        val timestamp = viewModel.requests.value!![position].timestamp
        holder.bind(req, timestamp)
    }
}
