package com.mytiki.tiki_sdk_android.example_app

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mytiki.tiki_sdk_android.example_app.databinding.WalletItemBinding

class WalletListAdapter(private val viewModel: HomeViewModel) :
    RecyclerView.Adapter<WalletListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WalletListViewHolder {
        val binding: WalletItemBinding = WalletItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return WalletListViewHolder(binding, viewModel)
    }

    override fun getItemCount(): Int {
        return viewModel.wallets.value!!.size
    }

    override fun onBindViewHolder(holder: WalletListViewHolder, position: Int) {
        val address = viewModel.wallets.value?.get(position)
        holder.bind(address!!)
    }
}
