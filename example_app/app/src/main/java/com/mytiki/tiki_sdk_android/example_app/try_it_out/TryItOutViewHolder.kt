package com.mytiki.tiki_sdk_android.example_app.wallet

import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.mytiki.tiki_sdk_android.TikiSdk
import com.mytiki.tiki_sdk_android.example_app.R
import com.mytiki.tiki_sdk_android.example_app.databinding.RequestItemBinding
import com.mytiki.tiki_sdk_android.example_app.databinding.WalletItemBinding
import java.security.Timestamp

class TryItOutViewHolder(private val binding: RequestItemBinding?) : RecyclerView.ViewHolder(binding!!.root) {
    fun bind(req: String, timestamp: String) {
        itemView.findViewById<TextView>(R.id.req).text = req
        itemView.findViewById<TextView>(R.id.timestamp).text = timestamp
    }
}