package com.mytiki.tiki_sdk_android.example_app.try_it_out

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.mytiki.tiki_sdk_android.example_app.R
import com.mytiki.tiki_sdk_android.example_app.databinding.TryItOutFragmentBinding

class TryItOutFragment : Fragment() {

    companion object {
        fun newInstance() = TryItOutFragment()
    }

    private val viewModel by activityViewModels<TryItOutViewModel>()

    private var _binding: TryItOutFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = TryItOutFragmentBinding.inflate(inflater, container, false)
        binding.walletCard.setOnClickListener{
            Navigation.findNavController(binding.root).navigate(R.id.action_try_it_out_to_walletListFragment)
        }
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        if(viewModel.selectedWalletAddress.value != null){
            binding.walletAddress.text = viewModel.selectedWalletAddress.value
            binding.ownershipAndConsent.visibility = View.VISIBLE
            binding.ownershipId.text = viewModel.ownership!!.transactionId
            binding.consentId.text = viewModel.consent?.transactionId
            binding.toggleConsent.isChecked = viewModel.isConsentGiven.value == true
        }
        val stream = viewModel.stream.value!!
        binding.destinationTitle.text = stream.httpMethod + " " + stream.url
        binding.bodyTitle.text = stream.body
    }
}