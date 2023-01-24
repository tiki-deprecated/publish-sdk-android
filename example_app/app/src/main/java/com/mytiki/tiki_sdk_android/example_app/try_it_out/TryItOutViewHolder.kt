package com.mytiki.tiki_sdk_android.example_app.wallet

import android.content.Context
import android.content.DialogInterface
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.mytiki.tiki_sdk_android.TikiSdk
import com.mytiki.tiki_sdk_android.example_app.R
import com.mytiki.tiki_sdk_android.example_app.databinding.RequestItemBinding
import com.mytiki.tiki_sdk_android.example_app.databinding.WalletItemBinding
import com.mytiki.tiki_sdk_android.example_app.try_it_out.TryItOutReq
import java.security.Timestamp

class TryItOutViewHolder(private val binding: RequestItemBinding?) : RecyclerView.ViewHolder(binding!!.root) {
    fun bind(req: TryItOutReq, context: Context) {
        itemView.findViewById<TextView>(R.id.req).text = req.icon + " " + req.message
        itemView.findViewById<TextView>(R.id.timestamp).text = req.timestamp
        itemView.setOnClickListener{
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Response Body")
            builder.setMessage(req.message)
            builder.show()
        }
    }
}