package com.mytiki.tiki_sdk_android.ui.activities

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.mytiki.tiki_sdk_android.LicenseUsecase
import com.mytiki.tiki_sdk_android.R
import com.mytiki.tiki_sdk_android.TikiSdk
import com.mytiki.tiki_sdk_android.ui.Offer
import com.mytiki.tiki_sdk_android.ui.Permission
import com.mytiki.tiki_sdk_android.ui.Theme
import io.noties.markwon.Markwon

class SettingsActivity : AppCompatActivity() {

    lateinit var offer: Offer
    lateinit var tikiSdkBtn: RelativeLayout
    lateinit var theme: Theme

    private val isPermissionPending: Boolean
        get() = permissions.size > 0

    private var permissions: MutableList<Permission> = mutableListOf()
    private lateinit var btnFrame: FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings)
        theme = TikiSdk.theme(this)
        offer = TikiSdk.offers.values.first()
        tikiSdkBtn = findViewById(R.id.tiki_sdk_btn)
        setupTradeYourDataTitle()
        setupOfferDetails()
        setupStaticBtns()
        setupOptBtn()
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

    private fun setupTradeYourDataTitle() {
        val root = findViewById<LinearLayout>(R.id.settings_root)
        val tradeText = root.findViewById<TextView>(R.id.trade_text)!!
        tradeText.typeface = ResourcesCompat.getFont(this, theme.fontBold)
        tradeText.setTextColor(theme.primaryTextColor)
        val yourText = root.findViewById<TextView>(R.id.your_text)!!
        yourText.typeface = ResourcesCompat.getFont(this, theme.fontBold)
        yourText.setTextColor(theme.accentColor)
        val dataText = root.findViewById<TextView>(R.id.data_text)!!
        dataText.typeface = ResourcesCompat.getFont(this, theme.fontBold)
        dataText.setTextColor(theme.primaryTextColor)
    }

    private fun setupOfferDetails() {
        findViewById<ImageView>(R.id.offer_image).setImageDrawable(offer.reward)

        val offerDescription =
            findViewById<LinearLayout>(R.id.offer_card_root).findViewById<TextView>(R.id.offer_description)
        offerDescription.text = offer.description
        offerDescription.setTextColor(theme.secondaryTextColor)
        offerDescription.typeface = ResourcesCompat.getFont(this, theme.fontBold)

        val usedFor = findViewById<LinearLayout>(R.id.used_for_root)!!
        val usedForTitle = usedFor.findViewById<TextView>(R.id.used_for_title)
        usedForTitle.setTextColor(theme.primaryTextColor)
        usedForTitle.typeface = ResourcesCompat.getFont(this, theme.fontBold)
        usedForTitle.text = "HOW YOUR DATA WILL BE USED"

        findViewById<ImageView>(R.id.bullet_icon_1).setImageDrawable(
            if (offer.bullets[0].isUsed) {
                ResourcesCompat.getDrawable(resources, R.drawable.ic_check_icon, null)
            } else {
                ResourcesCompat.getDrawable(resources, R.drawable.ic_x_icon, null)
            }
        )
        val bulletTxt1 = findViewById<TextView>(R.id.bullet_text_1)
        bulletTxt1.text = offer.bullets[0].text
        bulletTxt1.setTextColor(theme.secondaryTextColor)
        bulletTxt1.typeface = ResourcesCompat.getFont(this, theme.fontBold)

        findViewById<ImageView>(R.id.bullet_icon_2).setImageDrawable(
            if (offer.bullets[1].isUsed) {
                ResourcesCompat.getDrawable(resources, R.drawable.ic_check_icon, null)
            } else {
                ResourcesCompat.getDrawable(resources, R.drawable.ic_x_icon, null)
            }
        )
        val bulletTxt2 = findViewById<TextView>(R.id.bullet_text_2)
        bulletTxt2.text = offer.bullets[1].text
        bulletTxt2.setTextColor(theme.secondaryTextColor)
        bulletTxt2.typeface = ResourcesCompat.getFont(this, theme.fontBold)

        findViewById<ImageView>(R.id.bullet_icon_3).setImageDrawable(
            if (offer.bullets[2].isUsed) {
                ResourcesCompat.getDrawable(resources, R.drawable.ic_check_icon, null)
            } else {
                ResourcesCompat.getDrawable(resources, R.drawable.ic_x_icon, null)
            }
        )
        val bulletTxt3 = findViewById<TextView>(R.id.bullet_text_3)
        bulletTxt3.text = offer.bullets[2].text
        bulletTxt3.setTextColor(theme.secondaryTextColor)
        bulletTxt3.typeface = ResourcesCompat.getFont(this, theme.fontBold)

        val termsTitle = findViewById<TextView>(R.id.terms_settings_title)
        termsTitle.setTextColor(theme.primaryTextColor)
        termsTitle.typeface = ResourcesCompat.getFont(this, theme.fontBold)

        val termsTxt = findViewById<TextView>(R.id.terms_text)
        termsTxt.text = offer.bullets[2].text
        termsTxt.setTextColor(theme.primaryTextColor)
        termsTxt.typeface = ResourcesCompat.getFont(this, theme.fontRegular)

        val markwon = Markwon.create(this)
        markwon.setMarkdown(termsTxt, offer.terms)
    }

    private fun setupStaticBtns() {
        findViewById<ImageView>(R.id.question_icon)!!.setOnClickListener {
            val intent = Intent(this, LearnMoreActivity::class.java)
            startActivity(intent)
        }
        findViewById<ImageView>(R.id.back_btn).setOnClickListener {
            finish()
        }
    }

    private fun setupOptBtn() {
        val ptr: String = offer.ptr
        val usecases: MutableList<LicenseUsecase> = mutableListOf()
        val destinations: MutableList<String> = mutableListOf()
        offer.uses.forEach {
            if (it.destinations != null) {
                destinations.addAll(it.destinations)
            }
            usecases.addAll(it.usecases)
        }
        TikiSdk.guard(ptr, usecases, destinations, {
            enableOptOutBtn()
        }, {
            enableOptInBtn()
        })
    }

    private fun enableOptInBtn() {
        val solidBg = GradientDrawable()
        solidBg.shape = GradientDrawable.RECTANGLE
        solidBg.setTint(theme.accentColor)
        solidBg.cornerRadius = 30F
        val btnLabel = tikiSdkBtn.findViewById<TextView>(R.id.tiki_sdk_btn_label)
        btnLabel.setTextColor(theme.primaryBackgroundColor)
        btnLabel.text = "Opt in"
        btnLabel.typeface = ResourcesCompat.getFont(this, theme.fontRegular)
        tikiSdkBtn.background = solidBg

        tikiSdkBtn.setOnClickListener {
            permissions = offer.permissions.toMutableList()
            handlePermissions()
        }
    }

    private fun enableOptOutBtn() {
        val outlineBg = GradientDrawable()
        outlineBg.shape = GradientDrawable.RECTANGLE
        outlineBg.setStroke(4, theme.accentColor)
        outlineBg.setColor(theme.primaryBackgroundColor)
        outlineBg.cornerRadius = 30F

        tikiSdkBtn.background = outlineBg
        tikiSdkBtn.findViewById<TextView>(R.id.tiki_sdk_btn_label)
            .setTextColor(theme.primaryTextColor)
        tikiSdkBtn.findViewById<TextView>(R.id.tiki_sdk_btn_label)
            .text = "Opt out"

        tikiSdkBtn.setOnClickListener {
            TikiSdk.license(
                offer.ptr,
                listOf(),
                offer.terms,
                offer.tags,
                null,
                offer.description,
                offer.expiry
            ).invokeOnCompletion {
                setupOptBtn()
            }
        }
    }

    private fun handlePermissions() {
        if (!isPermissionPending) {
            TikiSdk.license(
                offer.ptr,
                offer.uses,
                offer.terms,
                offer.tags,
                null,
                offer.description,
                offer.expiry
            ).invokeOnCompletion {
                setupOptBtn()
            }
        } else {
            val perm = permissions.first()
            if (!perm.isAuthorized(this)) {
                perm.requestAuth(this)
            } else {
                permissions.removeFirst()
                handlePermissions()
            }
        }
    }

}