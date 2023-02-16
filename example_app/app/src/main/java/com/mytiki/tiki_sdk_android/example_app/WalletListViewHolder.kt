package com.mytiki.tiki_sdk_android.example_app.wallet

import android.graphics.Typeface
import android.view.View
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.mytiki.tiki_sdk_android.example_app.databinding.WalletItemBinding
import com.mytiki.tiki_sdk_android.example_app.try_it_out.TryItOutViewModel

class WalletListViewHolder(
    private val binding: WalletItemBinding?,
    private val viewModel: TryItOutViewModel
) : RecyclerView.ViewHolder(binding!!.root) {
    fun bind(tikiSdkAddress: String) {
        binding!!.addressTextView.text = tikiSdkAddress
        if(tikiSdkAddress == viewModel.selectedWalletAddress.value){
            binding.addressTextView.setTypeface(null, Typeface.BOLD);
        }
        itemView.setOnClickListener {
            viewModel.loadTikiSdk(itemView.context, tikiSdkAddress)
            Navigation.findNavController(itemView).popBackStack()
        }
    }
}