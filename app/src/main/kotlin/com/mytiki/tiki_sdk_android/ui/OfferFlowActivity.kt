package com.mytiki.tiki_sdk_android.ui

import android.app.Activity
import android.content.Intent
import android.content.Intent.*
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
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

    private var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                promptBottomSheetDialog.dismiss()
                permissions = offer.permissions.toMutableList()
                if (isPermissionPending) {
                    handlePermissions()
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
        if (promptBottomSheetDialog.isShowing) {
            promptBottomSheetDialog.dismiss()
        }
        endingErrorBottomSheetDialog.show()
        endingErrorBottomSheetDialog.findViewById<TextView>(R.id.permissions_link)!!.text =
            permissions.map { permission -> permission.displayName }.joinToString(", ") + "."
        endingErrorBottomSheetDialog.findViewById<TextView>(R.id.permissions_link)!!
            .setOnClickListener {
                val intent = Intent(ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                    data = Uri.fromParts("package", packageName, null)
                    addCategory(CATEGORY_DEFAULT)
                    addFlags(FLAG_ACTIVITY_NEW_TASK)
                    addFlags(FLAG_ACTIVITY_NO_HISTORY)
                    addFlags(FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
                }
                startActivity(intent)
            }
    }

    private fun handlePermissions() {
        if (!isPermissionPending) {
            showEndingAccepted()
        } else {
            showEndingError()
            val perm = permissions.first()
            Log.e("tiki", perm.name)
            if (!perm.isAuthorized(this)) {
                perm.requestAuth(this)
            } else {
                permissions.removeFirst()
                handlePermissions()
            }
        }
    }

    private fun showEndingDeclined() {
        step = OfferFlowStep.ENDING_DECLINED
        promptBottomSheetDialog.dismiss()
        endingDeclinedBottomSheetDialog.show()
        enableSettingsLink(endingDeclinedBottomSheetDialog)
    }

    private fun showEndingAccepted() {
        TikiSdk.license(
            offer.ptr,
            offer.uses,
            offer.terms,
            offer.tags,
            null,
            offer.description,
            offer.expiry
        )
        step = OfferFlowStep.ENDING_ACCEPTED
        endingErrorBottomSheetDialog.apply {
            if (this.isShowing) {
                this.dismiss()
            }
        }
        promptBottomSheetDialog.apply {
            if (this.isShowing) {
                this.dismiss()
            }
        }
        endingAcceptedBottomSheetDialog.show()
        enableSettingsLink(endingAcceptedBottomSheetDialog)
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

    private fun enableSettingsLink(v: BottomSheetDialog) {
        v.findViewById<TextView>(R.id.settings_link)!!.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            finish()
            startActivity(intent)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        Log.e("tiki", "callback")
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            this.permissions.removeFirst()
            handlePermissions()
        }
    }
}