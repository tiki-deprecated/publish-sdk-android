package com.mytiki.tiki_sdk_android.example_app.wallet

import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.mytiki.tiki_sdk_android.TikiSdk
import com.mytiki.tiki_sdk_android.example_app.R
import com.mytiki.tiki_sdk_android.example_app.databinding.WalletItemBinding
import com.mytiki.tiki_sdk_android.example_app.try_it_out.TryItOutViewModel

class WalletListViewHolder(private val binding: WalletItemBinding?, private val viewModel: TryItOutViewModel) : RecyclerView.ViewHolder(binding!!.root) {
    fun bind(tikiSdkAddress: String) {
        binding!!.addressTextView.text = tikiSdkAddress
        itemView.setOnClickListener {
            viewModel.loadTikiSdk(itemView.context, tikiSdkAddress)
            Navigation.findNavController(itemView).popBackStack()
        }
    }
}