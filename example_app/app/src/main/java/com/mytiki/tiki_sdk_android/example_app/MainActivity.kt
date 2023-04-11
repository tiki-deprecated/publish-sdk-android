package com.mytiki.tiki_sdk_android.example_app

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.mytiki.tiki_sdk_android.LicenseUsecase
import com.mytiki.tiki_sdk_android.LicenseUsecaseEnum
import com.mytiki.tiki_sdk_android.TikiSdk
import com.mytiki.tiki_sdk_android.TitleTag
import com.mytiki.tiki_sdk_android.ui.Permission
import java.util.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TikiSdk.offer
            .ptr("test_offer")
            .reward(ResourcesCompat.getDrawable(resources, R.drawable.offer_img, null)!!)
            .bullet("Learn how our ads perform ", true)
            .bullet("Reach you on other platforms", false)
            .bullet("Sold to other companies", false)
            .use(listOf(LicenseUsecase(LicenseUsecaseEnum.SUPPORT)))
            .permission(Permission.CAMERA)
            .tag(TitleTag.ADVERTISING_DATA)
            .description("Trade your IDFA (kind of like a serial # for your phone) for a discount.")
            .terms(this, "terms.md")
            .duration(365, TimeUnit.DAYS)
            .add()
            .onAccept { _, _ -> print("accepted") }
            .onDecline { _, _ -> print("declined") }
            //.onSettings  { print("settings") }
            .disableAcceptEnding(false)
            .disableDeclineEnding(false)
            .init(this, "e12f5b7b-6b48-4503-8b39-28e4995b5f88", UUID.randomUUID().toString()) {
                setContentView(R.layout.activity_main)
                findViewById<Button>(R.id.start).setOnClickListener {
                    TikiSdk.present(this)
                }
                findViewById<Button>(R.id.settings).setOnClickListener {
                    TikiSdk.settings(this)
                }
            }
    }
}