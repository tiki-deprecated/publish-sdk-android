package com.mytiki.tiki_sdk_android.example_app

import android.content.Context
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.mytiki.tiki_sdk_android.example_app.databinding.RequestItemBinding

class RequestsViewHolder(private val binding: RequestItemBinding?) :
    RecyclerView.ViewHolder(binding!!.root) {
    fun bind(req: RequestModel, context: Context) {
        itemView.findViewById<TextView>(R.id.req).text = req.icon + " " + req.message
        itemView.findViewById<TextView>(R.id.timestamp).text = req.timestamp
        itemView.setOnClickListener {
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Response Body")
            builder.setMessage(req.message)
            builder.show()
        }
    }
}