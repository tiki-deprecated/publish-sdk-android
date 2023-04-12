package com.mytiki.tiki_sdk_android.ui.activities

import android.app.Activity
import android.content.Intent
import android.content.Intent.*
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.mytiki.tiki_sdk_android.R
import com.mytiki.tiki_sdk_android.TikiSdk
import com.mytiki.tiki_sdk_android.ui.Offer
import com.mytiki.tiki_sdk_android.ui.OfferFlowStep
import com.mytiki.tiki_sdk_android.ui.Permission
import com.mytiki.tiki_sdk_android.ui.Theme


class OfferFlowActivity : AppCompatActivity() {

    private val offer: Offer = TikiSdk.offers.values.first()
    private var permissions: MutableList<Permission> = mutableListOf()
    private val isPermissionPending: Boolean
        get() = permissions.size > 0

    private var step: OfferFlowStep = OfferFlowStep.PROMPT
    private var termsResult =
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

    private lateinit var promptBottomSheetDialog: BottomSheetDialog
    private lateinit var endingAcceptedBottomSheetDialog: BottomSheetDialog
    private lateinit var endingDeclinedBottomSheetDialog: BottomSheetDialog
    private lateinit var endingErrorBottomSheetDialog: BottomSheetDialog
    private lateinit var theme: Theme

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        theme = TikiSdk.theme(this)
        setContentView(R.layout.activity_offer_flow)
        initializeBottomSheets()
        showOfferPrompt()
        findViewById<RelativeLayout>(R.id.activity_offer_bg).setOnClickListener {
            finish()
        }
    }

    private fun initializeBottomSheets() {
        initPromptBottomSheet()
        initEndingAcceptedBottomSheet()
        initEndingDeclinedBottomSheet()
        initEndingErrorBottomSheet()
    }

    private fun initEndingErrorBottomSheet() {
        endingErrorBottomSheetDialog = BottomSheetDialog(this)
        endingErrorBottomSheetDialog.setContentView(R.layout.ending_error)
        endingErrorBottomSheetDialog.setOnDismissListener {
            if (step == OfferFlowStep.ENDING_ERROR) {
                finish()
            }
        }
    }

    private fun initEndingDeclinedBottomSheet() {
        endingDeclinedBottomSheetDialog = BottomSheetDialog(this)
        endingDeclinedBottomSheetDialog.setContentView(R.layout.ending_declined)
        endingDeclinedBottomSheetDialog.setOnDismissListener {
            if (step == OfferFlowStep.ENDING_DECLINED) {
                finish()
            }
        }
    }

    private fun initEndingAcceptedBottomSheet() {
        endingAcceptedBottomSheetDialog = BottomSheetDialog(this)
        endingAcceptedBottomSheetDialog.setContentView(R.layout.ending_accepted)
        endingAcceptedBottomSheetDialog.setOnDismissListener {
            if (step == OfferFlowStep.ENDING_ACCEPTED) {
                finish()
            }
        }
    }

    private fun initPromptBottomSheet() {
        promptBottomSheetDialog = BottomSheetDialog(this)
        promptBottomSheetDialog.setContentView(R.layout.offer_prompt)

        val drawable = promptBottomSheetDialog
            .findViewById<LinearLayout>(R.id.offer_prompt_root)!!.background
        drawable.setTint(TikiSdk.theme.secondaryBackgroundColor)

        setupTradeYourDataTitle()
        setupOfferDetails()
        setupPromptButtons()
    }

    private fun setupPromptButtons() {
        val solidBg = GradientDrawable()
        solidBg.shape = GradientDrawable.RECTANGLE
        solidBg.setTint(theme.accentColor)
        solidBg.cornerRadius = 10F

        val optInFrame = promptBottomSheetDialog.findViewById<FrameLayout>(R.id.opt_in)!!
        val optInBtn = optInFrame.findViewById<RelativeLayout>(R.id.tiki_sdk_btn)
        optInBtn.setOnClickListener{ showTerms() }
        optInBtn.background = solidBg

        val optInBtnLabel = optInBtn.findViewById<TextView>(R.id.tiki_sdk_btn_label)
        optInBtnLabel.setTextColor(theme.primaryBackgroundColor)
        optInBtnLabel.typeface = ResourcesCompat.getFont(this, theme.fontMedium)
        optInBtnLabel.text = "I'm in"

        val outlineBg = GradientDrawable()
        outlineBg.shape = GradientDrawable.RECTANGLE
        outlineBg.setTint(theme.primaryBackgroundColor)
        outlineBg.setStroke(2, theme.accentColor)
        outlineBg.cornerRadius = 10F

        val optOutFrame = promptBottomSheetDialog.findViewById<FrameLayout>(R.id.opt_out)!!
        val optOutBtn = optOutFrame.findViewById<RelativeLayout>(R.id.tiki_sdk_btn)!!
        optOutBtn.setOnClickListener{ showEndingDeclined() }
        optOutBtn.background = outlineBg

        val optOutBtnLabel = optOutBtn.findViewById<TextView>(R.id.tiki_sdk_btn_label)
        optOutBtnLabel.setTextColor(theme.primaryTextColor)
        optOutBtnLabel.typeface = ResourcesCompat.getFont(this, theme.fontMedium)
        optOutBtnLabel.text = "Back Off"

        val learnMoreButton = promptBottomSheetDialog.findViewById<ImageView>(R.id.question_icon)!!
        learnMoreButton.setOnClickListener { showLearnMore() }
        learnMoreButton.setColorFilter(theme.secondaryBackgroundColor)

    }

    private fun setupTradeYourDataTitle() {
        val tradeText = promptBottomSheetDialog.findViewById<TextView>(R.id.trade_text)!!
        tradeText.typeface = ResourcesCompat.getFont(this, theme.fontBold)
        tradeText.setTextColor(theme.primaryTextColor)
        val yourText = promptBottomSheetDialog.findViewById<TextView>(R.id.your_text)!!
        yourText.typeface = ResourcesCompat.getFont(this, theme.fontBold)
        yourText.setTextColor(theme.accentColor)
        val dataText = promptBottomSheetDialog.findViewById<TextView>(R.id.data_text)!!
        dataText.typeface = ResourcesCompat.getFont(this, theme.fontBold)
        dataText.setTextColor(theme.primaryTextColor)
    }

    private fun setupOfferDetails() {
        val offerCard = promptBottomSheetDialog.findViewById<LinearLayout>(R.id.offer_card_root)!!
        offerCard.findViewById<ImageView>(R.id.offer_image).setImageDrawable(offer.reward)
        val offerDescription = offerCard.findViewById<TextView>(R.id.offer_description)
        offerDescription.text = offer.description
        offerDescription.setTextColor(theme.secondaryTextColor)
        offerDescription.typeface = ResourcesCompat.getFont(this, theme.fontBold)

        val usedFor = promptBottomSheetDialog.findViewById<LinearLayout>(R.id.used_for_root)!!
        val usedForTitle = usedFor.findViewById<TextView>(R.id.used_for_title)
        usedForTitle.setTextColor(Color.BLACK)
        usedForTitle.typeface = ResourcesCompat.getFont(this, theme.fontBold)

        usedFor.findViewById<ImageView>(R.id.bullet_icon_1).setImageDrawable(
            if (offer.bullets[0].isUsed) {
                ResourcesCompat.getDrawable(resources, R.drawable.ic_check_icon, null)
            } else {
                ResourcesCompat.getDrawable(resources, R.drawable.ic_x_icon, null)
            }
        )
        val bulletText1 = usedFor.findViewById<TextView>(R.id.bullet_text_1)
        bulletText1.text = offer.bullets[0].text
        bulletText1.setTextColor(theme.secondaryTextColor)
        bulletText1.typeface = ResourcesCompat.getFont(this, theme.fontBold)

        usedFor.findViewById<ImageView>(R.id.bullet_icon_2).setImageDrawable(
            if (offer.bullets[1].isUsed) {
                ResourcesCompat.getDrawable(resources, R.drawable.ic_check_icon, null)
            } else {
                ResourcesCompat.getDrawable(resources, R.drawable.ic_x_icon, null)
            }
        )
        val bulletText2 = usedFor.findViewById<TextView>(R.id.bullet_text_2)
        bulletText2.text = offer.bullets[1].text
        bulletText2.setTextColor(theme.secondaryTextColor)
        bulletText2.typeface = ResourcesCompat.getFont(this, theme.fontBold)

        usedFor.findViewById<ImageView>(R.id.bullet_icon_3).setImageDrawable(
            if (offer.bullets[2].isUsed) {
                ResourcesCompat.getDrawable(resources, R.drawable.ic_check_icon, null)
            } else {
                ResourcesCompat.getDrawable(resources, R.drawable.ic_x_icon, null)
            }
        )
        val bulletText3 = usedFor.findViewById<TextView>(R.id.bullet_text_3)
        bulletText3.text = offer.bullets[2].text
        bulletText3.setTextColor(theme.secondaryTextColor)
        bulletText3.typeface = ResourcesCompat.getFont(this, theme.fontBold)
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

    private fun showEndingDeclined() {
        step = OfferFlowStep.ENDING_DECLINED
        promptBottomSheetDialog.dismiss()
        if(TikiSdk.isDeclineEndingDisabled){
            finish()
        }else {
            endingDeclinedBottomSheetDialog.show()
            enableSettingsLink(endingDeclinedBottomSheetDialog)
        }
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
        if(TikiSdk.isAcceptEndingDisabled){
            finish()
        }else {
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
        termsResult.launch(intent)
    }

    private fun enableSettingsLink(v: BottomSheetDialog) {
        v.findViewById<TextView>(R.id.settings_link)!!.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            finish()
            startActivity(intent)
        }
    }

    private fun handlePermissions() {
        if (!isPermissionPending) {
            showEndingAccepted()
        } else {
            showEndingError()
            val perm = permissions.first()
            if (!perm.isAuthorized(this)) {
                perm.requestAuth(this)
            } else {
                permissions.removeFirst()
                handlePermissions()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            this.permissions.removeFirst()
            handlePermissions()
        }
    }
}