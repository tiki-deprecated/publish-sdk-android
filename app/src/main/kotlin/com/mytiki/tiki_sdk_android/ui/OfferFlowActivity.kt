package com.mytiki.tiki_sdk_android.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.mytiki.tiki_sdk_android.R
import com.mytiki.tiki_sdk_android.TikiSdk

class OfferFlowActivity : AppCompatActivity() {

    private var step: OfferFlowStep = OfferFlowStep.PROMPT

    private lateinit var promptBottomSheetDialog: BottomSheetDialog
    private lateinit var endingAcceptedBottomSheetDialog: BottomSheetDialog
    private lateinit var endingDeclinedBottomSheetDialog: BottomSheetDialog
    private lateinit var endingErrorBottomSheetDialog: BottomSheetDialog

    private val offer: Offer = TikiSdk.offers.values.first()
    private val isPermissionPending: Boolean
        get() = permissions.size > 0
    private var permissions: MutableList<Permission> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_offer_flow)
        initializeBottomSheets()
        showOfferPrompt()
        findViewById<ConstraintLayout>(R.id.activity_offer_bg).setOnClickListener {
            finish()
        }
    }

    private var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            promptBottomSheetDialog.dismiss()
            permissions = offer.permissions.toMutableList()
            if (isPermissionPending) {
                step = OfferFlowStep.ENDING_ERROR
                showEndingError()
            } else {
                step = OfferFlowStep.ENDING_ACCEPTED
                showEndingAccepted()
            }
        }
    }

    private fun initializeBottomSheets() {
        promptBottomSheetDialog = BottomSheetDialog(this)
        promptBottomSheetDialog.setContentView(R.layout.offer_prompt)
        promptBottomSheetDialog.findViewById<ConstraintLayout>(R.id.color_btn)!!
            .setOnClickListener {
                showTerms()
            }
        promptBottomSheetDialog.findViewById<ImageView>(R.id.question_icon)!!.setOnClickListener {
            showLearnMore()
        }
        promptBottomSheetDialog.findViewById<ConstraintLayout>(R.id.outline_btn)!!
            .setOnClickListener {
                showEndingDeclined()
            }
        promptBottomSheetDialog.setOnDismissListener {
            if (step == OfferFlowStep.PROMPT) {
                finish()
            }
        }
        endingAcceptedBottomSheetDialog = BottomSheetDialog(this)
        endingAcceptedBottomSheetDialog.setContentView(R.layout.ending_accepted)
        endingAcceptedBottomSheetDialog.setOnDismissListener {
            if (step == OfferFlowStep.ENDING_ACCEPTED) {
                finish()
            }
        }
        endingDeclinedBottomSheetDialog = BottomSheetDialog(this)
        endingDeclinedBottomSheetDialog.setContentView(R.layout.ending_declined)
        endingDeclinedBottomSheetDialog.setOnDismissListener {
            if (step == OfferFlowStep.ENDING_DECLINED) {
                finish()
            }
        }

        endingErrorBottomSheetDialog = BottomSheetDialog(this)
        endingErrorBottomSheetDialog.setContentView(R.layout.ending_error)
        endingErrorBottomSheetDialog.setOnDismissListener {
            if (step == OfferFlowStep.ENDING_ERROR) {
                finish()
            }
        }

    }

    private fun showEndingError() {
        step = OfferFlowStep.ENDING_ERROR
        promptBottomSheetDialog.dismiss()
        endingErrorBottomSheetDialog.show()
    }

    private fun showEndingDeclined() {
        step = OfferFlowStep.ENDING_DECLINED
        promptBottomSheetDialog.dismiss()
        endingDeclinedBottomSheetDialog.show()
    }

    private fun showEndingAccepted() {
        step = OfferFlowStep.ENDING_ACCEPTED
        promptBottomSheetDialog.dismiss()
        endingAcceptedBottomSheetDialog.show()
    }

    private fun showOfferPrompt() {
        step = OfferFlowStep.PROMPT
        promptBottomSheetDialog.show()
    }

    private fun showLearnMore() {
        val intent = Intent(this, LearnMoreActivity::class.java)
        startActivity(intent)
    }

    private fun showTerms() {
        val intent = Intent(this, TermsActivity::class.java)
        resultLauncher.launch(intent)
    }
}