package com.mytiki.tiki_sdk_android.example_app.stream.log

import android.os.Bundle
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.mytiki.tiki_sdk_android.example_app.R
import com.mytiki.tiki_sdk_android.example_app.databinding.StreamLogItemBinding
import com.mytiki.tiki_sdk_android.example_app.stream.Stream


class StreamLogViewHolder(private val binding: StreamLogItemBinding?) : RecyclerView.ViewHolder(binding!!.root) {
    fun bind(stream: Stream) {
//        itemView.findViewById<TextView>(R.id.addressTextView).text = stream.data
//        itemView.setOnClickListener {
//            val bundle = Bundle()
//            bundle.putParcelable("stream", stream)
//            Navigation.findNavController(itemView).navigate(R.id.action_streamListFragment_to_logFragment, bundle)
//        }
    }
}