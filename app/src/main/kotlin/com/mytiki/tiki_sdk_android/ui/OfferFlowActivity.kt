package com.mytiki.tiki_sdk_android.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.mytiki.tiki_sdk_android.R

class OfferFlowActivity : AppCompatActivity() {

    private val viewModel by viewModels<OfferFlowViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_offer_flow)
        viewModel.step.observe(this) {
            when (it) {
                OfferFlowStep.PROMPT -> dismiss()
                OfferFlowStep.PROMPT -> showOfferPrompt()
                OfferFlowStep.TERMS -> showTerms()
                OfferFlowStep.LEARN_MORE -> showLearnMore()
                OfferFlowStep.ENDING_ACCEPTED -> showEndingAccepted()
                OfferFlowStep.ENDING_DECLINED -> showEndingDeclined()
                OfferFlowStep.ENDING_ERROR -> showEndingError()
            }
        }
    }

    private fun dismiss() {
        TODO("Not yet implemented")
    }

    private fun showEndingError() {
        val bottomSheetDialog = BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.ending_error);
        bottomSheetDialog.show()
    }

    private fun showEndingDeclined() {
        val bottomSheetDialog = BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.ending_declined);
        bottomSheetDialog.show()
    }

    private fun showEndingAccepted() {
        TODO("Not yet implemented")
    }

    private fun showLearnMore() {
        TODO("Not yet implemented")
    }

    private fun showTerms() {
        TODO("Not yet implemented")
    }

    private fun showOfferPrompt() {
        TODO("Not yet implemented")
    }
}