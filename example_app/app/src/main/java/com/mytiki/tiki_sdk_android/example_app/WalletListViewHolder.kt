package com.mytiki.tiki_sdk_android.example_app

import android.graphics.Typeface
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.mytiki.tiki_sdk_android.example_app.databinding.WalletItemBinding

class WalletListViewHolder(
    private val binding: WalletItemBinding?,
    private val viewModel: HomeViewModel
) : RecyclerView.ViewHolder(binding!!.root) {
    fun bind(tikiSdkAddress: String) {
        binding!!.addressTextView.text = tikiSdkAddress
        if(tikiSdkAddress == viewModel.tikiSdk.address){
            binding.addressTextView.setTypeface(null, Typeface.BOLD)
        }
        itemView.setOnClickListener {
            viewModel.loadTikiSdk(itemView.context, tikiSdkAddress)
            Navigation.findNavController(itemView).popBackStack()
        }
    }
}