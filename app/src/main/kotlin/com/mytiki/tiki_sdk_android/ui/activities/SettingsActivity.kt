package com.mytiki.tiki_sdk_android.ui.activities

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.mytiki.tiki_sdk_android.LicenseUsecase
import com.mytiki.tiki_sdk_android.R
import com.mytiki.tiki_sdk_android.TikiSdk
import com.mytiki.tiki_sdk_android.ui.Offer
import com.mytiki.tiki_sdk_android.ui.Permission

class SettingsActivity : AppCompatActivity() {

    lateinit var offer: Offer
    lateinit var optInBtn: RelativeLayout
    lateinit var optOutBtn: RelativeLayout

    private val isPermissionPending: Boolean
        get() = permissions.size > 0

    private var permissions: MutableList<Permission> = mutableListOf()
    private lateinit var btnFrame: FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings)
        offer = TikiSdk.offers.values.first()
        btnFrame = findViewById(R.id.settings_btn_frame)
        setupOfferDetails()
        setupStaticBtns()
        setupOptBtn()
    }

    private fun setupOfferDetails() {
        findViewById<ImageView>(R.id.offer_image).setImageDrawable(offer.reward)
        findViewById<TextView>(R.id.offer_description).text = offer.description

        findViewById<ImageView>(R.id.bullet_icon_1).setImageDrawable(
            if (offer.bullets[0].isUsed) {
                ResourcesCompat.getDrawable(resources, R.drawable.ic_check_icon, null)
            } else {
                ResourcesCompat.getDrawable(resources, R.drawable.ic_x_icon, null)
            }
        )
        findViewById<TextView>(R.id.bullet_text_1).text = offer.bullets[0].text

        findViewById<ImageView>(R.id.bullet_icon_2).setImageDrawable(
            if (offer.bullets[1].isUsed) {
                ResourcesCompat.getDrawable(resources, R.drawable.ic_check_icon, null)
            } else {
                ResourcesCompat.getDrawable(resources, R.drawable.ic_x_icon, null)
            }
        )
        findViewById<TextView>(R.id.bullet_text_2).text = offer.bullets[1].text

        findViewById<ImageView>(R.id.bullet_icon_3).setImageDrawable(
            if (offer.bullets[2].isUsed) {
                ResourcesCompat.getDrawable(resources, R.drawable.ic_check_icon, null)
            } else {
                ResourcesCompat.getDrawable(resources, R.drawable.ic_x_icon, null)
            }
        )
        findViewById<TextView>(R.id.bullet_text_3).text = offer.bullets[2].text

        findViewById<TextView>(R.id.terms_text).text = offer.terms
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
        val inflater = LayoutInflater.from(this)
        optInBtn = inflater.inflate(R.layout.button_color, null, false) as RelativeLayout
        optOutBtn = inflater.inflate(R.layout.button_outline, null, false) as RelativeLayout
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
            Log.e("TIKI", "SETTINGS GUARD PASS")
            enableOptOutBtn()
        }, {


            Log.e("TIKI", "SETTINGS GUARD FAIL")
            enableOptInBtn()
        })
    }

    private fun enableOptInBtn() {
        optInBtn.findViewById<TextView>(R.id.color_btn_label).text = "Opt in"
        optInBtn.setOnClickListener {
            permissions = offer.permissions.toMutableList()
            handlePermissions()
        }
        btnFrame.removeAllViews()
        btnFrame.addView(optInBtn)
    }

    private fun enableOptOutBtn() {
        optOutBtn.findViewById<TextView>(R.id.color_btn_label).text = "Opt out"
        optOutBtn.setOnClickListener {
            TikiSdk.license(
                offer.ptr,
                listOf(),
                offer.terms,
                offer.tags,
                null,
                offer.description,
                offer.expiry
            ).invokeOnCompletion {
                Log.e("tiki", "complete")
                setupOptBtn()
            }
        }
        btnFrame.removeAllViews()
        btnFrame.addView(optInBtn)
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