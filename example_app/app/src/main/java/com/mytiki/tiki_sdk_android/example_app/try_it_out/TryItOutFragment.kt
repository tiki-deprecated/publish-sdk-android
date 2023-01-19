package com.mytiki.tiki_sdk_android.example_app.try_it_out

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mytiki.tiki_sdk_android.example_app.R

class TryItOutFragment : Fragment() {

    companion object {
        fun newInstance() = TryItOutFragment()
    }

    private lateinit var viewModel: TryItOutViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.try_it_out_fragment, container, false)
    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)
        viewModel = ViewModelProvider(this).get(TryItOutViewModel::class.java)

    }
}