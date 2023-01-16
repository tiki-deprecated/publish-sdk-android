package com.mytiki.tiki_sdk_android.example_app.wallet

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mytiki.tiki_sdk_android.example_app.databinding.WalletItemBinding

class WalletListAdapter(private val viewModel: WalletListViewModel) : RecyclerView.Adapter<WalletListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WalletListViewHolder {
        val binding: WalletItemBinding = WalletItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)
        return WalletListViewHolder(binding)
    }

    override fun getItemCount() : Int {
        val size = viewModel.wallets.value!!.size
        return size
    }

    override fun onBindViewHolder(holder: WalletListViewHolder, position: Int) {
        val tikiSdk = viewModel.wallets.value!![position]
        holder.bind(tikiSdk)
    }
}
