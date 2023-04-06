package com.mytiki.tiki_sdk_android.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.mytiki.tiki_sdk_android.R

class OfferFlowActivity : AppCompatActivity() {

    private val viewModel by viewModels<OfferFlowViewModel>()

    private lateinit var promptBottomSheetDialog: BottomSheetDialog
    private lateinit var endingAcceptedBottomSheetDialog: BottomSheetDialog
    private lateinit var endingDeclinedBottomSheetDialog: BottomSheetDialog
    private lateinit var endingErrorBottomSheetDialog: BottomSheetDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_offer_flow)
        initializeBottomSheets()
        viewModel.step.observe(this) {
            when (it) {
                OfferFlowStep.NONE, null -> dismiss()
                OfferFlowStep.PROMPT -> showOfferPrompt()
                OfferFlowStep.TERMS -> showTerms()
                OfferFlowStep.LEARN_MORE -> showLearnMore()
                OfferFlowStep.ENDING_ACCEPTED -> showEndingAccepted()
                OfferFlowStep.ENDING_DECLINED -> showEndingDeclined()
                OfferFlowStep.ENDING_ERROR -> showEndingError()
            }
        }
    }

    private fun initializeBottomSheets() {
        promptBottomSheetDialog = BottomSheetDialog(applicationContext)
        promptBottomSheetDialog.setContentView(R.layout.offer_prompt)
        endingAcceptedBottomSheetDialog = BottomSheetDialog(applicationContext)
        endingAcceptedBottomSheetDialog.setContentView(R.layout.ending_accepted)
        endingDeclinedBottomSheetDialog = BottomSheetDialog(applicationContext)
        endingDeclinedBottomSheetDialog.setContentView(R.layout.ending_declined)
        endingErrorBottomSheetDialog = BottomSheetDialog(applicationContext)
        endingErrorBottomSheetDialog.setContentView(R.layout.ending_error)
    }

    private fun dismiss() {
        finishActivity(1001)
    }

    private fun showEndingError() {
        endingErrorBottomSheetDialog.show()
    }

    private fun showEndingDeclined() {
        endingDeclinedBottomSheetDialog.show()
    }

    private fun showEndingAccepted() {
        endingAcceptedBottomSheetDialog.show()
    }

    private fun showOfferPrompt() {
        promptBottomSheetDialog.show()
    }

    private fun showLearnMore() {

    }

    private fun showTerms() {

    }
}