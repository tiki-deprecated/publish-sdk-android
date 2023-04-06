package com.mytiki.tiki_sdk_android.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
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
        showOfferPrompt()
    }

    private var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            viewModel.setPendingPermissions()
            if(viewModel.isPermissionPending){
                viewModel.step(OfferFlowStep.ENDING_ERROR)
            }else{
                viewModel.step(OfferFlowStep.ENDING_ACCEPTED)
            }
        }
    }

    private fun initializeBottomSheets() {
        promptBottomSheetDialog = BottomSheetDialog(this)
        promptBottomSheetDialog.setContentView(R.layout.offer_prompt)
        promptBottomSheetDialog.findViewById<ConstraintLayout>(R.id.color_btn)!!.setOnClickListener{
            val intent = Intent(this, TermsActivity::class.java)
            resultLauncher.launch(intent)
        }
        promptBottomSheetDialog.findViewById<ConstraintLayout>(R.id.outline_btn)!!.setOnClickListener{
            viewModel.step(OfferFlowStep.ENDING_DECLINED)
        }
        promptBottomSheetDialog.setOnDismissListener {
            dismiss()
        }
        endingAcceptedBottomSheetDialog = BottomSheetDialog(this)
        endingAcceptedBottomSheetDialog.setContentView(R.layout.ending_accepted)
        endingAcceptedBottomSheetDialog.setOnDismissListener {
                dismiss()
            }
        endingDeclinedBottomSheetDialog = BottomSheetDialog(this)
        endingDeclinedBottomSheetDialog.setContentView(R.layout.ending_declined)
        endingDeclinedBottomSheetDialog.setOnDismissListener {
                dismiss()
            }
        endingErrorBottomSheetDialog = BottomSheetDialog(this)
        endingErrorBottomSheetDialog.setContentView(R.layout.ending_error)
        endingErrorBottomSheetDialog.setOnDismissListener {
                dismiss()
            }
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