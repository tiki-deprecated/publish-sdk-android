package com.mytiki.tiki_sdk_android.example_app.wallet

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mytiki.tiki_sdk_android.example_app.databinding.WalletItemBinding
import com.mytiki.tiki_sdk_android.example_app.try_it_out.TryItOutViewModel

class WalletListAdapter(private val viewModel: TryItOutViewModel) : RecyclerView.Adapter<WalletListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WalletListViewHolder {
        val binding: WalletItemBinding = WalletItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)
        return WalletListViewHolder(binding, viewModel)
    }

    override fun getItemCount() : Int {
        val size = viewModel.wallets.value!!.size
        return size
    }

    override fun onBindViewHolder(holder: WalletListViewHolder, position: Int) {
        val address = viewModel.wallets.value?.keys?.toList()?.get(position)
        holder.bind(address!!)
    }
}
