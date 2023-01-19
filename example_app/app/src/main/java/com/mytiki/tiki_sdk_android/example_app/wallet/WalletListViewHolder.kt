package com.mytiki.tiki_sdk_android.example_app.wallet

import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.mytiki.tiki_sdk_android.TikiSdk
import com.mytiki.tiki_sdk_android.example_app.R
import com.mytiki.tiki_sdk_android.example_app.databinding.WalletItemBinding

class WalletListViewHolder(private val binding: WalletItemBinding?) : RecyclerView.ViewHolder(binding!!.root) {
    fun bind(tikiSdkAddress: String) {
        itemView.findViewById<TextView>(R.id.addressTextView).text = tikiSdkAddress
        itemView.setOnClickListener {
//            Navigation.findNavController(itemView).navigate(R.id.action_wallet_fragment_to_stream_list_fragment, bundleOf())
        }
    }
}