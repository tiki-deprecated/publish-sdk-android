package com.mytiki.tiki_sdk_android.ui.activities

import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.mytiki.tiki_sdk_android.R
import com.mytiki.tiki_sdk_android.TikiSdk
import com.mytiki.tiki_sdk_android.ui.Theme
import io.noties.markwon.Markwon
import io.noties.markwon.ext.tables.TablePlugin

class TermsActivity : AppCompatActivity() {

    private lateinit var theme: Theme

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        theme = TikiSdk.theme(this)
        setContentView(R.layout.terms)

        val markwon = Markwon.builder(this)
            .usePlugin(TablePlugin.create(this))
            .build();
        markwon.setMarkdown(findViewById(R.id.terms_text), TikiSdk.offers.values.first().terms);

        val backBtn = findViewById<ImageView>(R.id.back_btn)
        backBtn.setOnClickListener {
            val returnIntent = Intent()
            setResult(RESULT_CANCELED, returnIntent)
            finish()
        }
        backBtn.drawable.setTint(theme.primaryTextColor)

        val title = findViewById<TextView>(R.id.terms_title)
        title.text = "Terms and Conditions"
        title.setTextColor(theme.primaryTextColor)
        title.typeface = ResourcesCompat.getFont(this, theme.fontBold)

        val iAgreeBtn = findViewById<RelativeLayout>(R.id.tiki_sdk_btn)
        iAgreeBtn.setOnClickListener {
            val returnIntent = Intent()
            setResult(RESULT_OK, returnIntent)
            finish()
        }
        val solidBg = GradientDrawable()
        solidBg.shape = GradientDrawable.RECTANGLE
        solidBg.setTint(theme.accentColor)
        solidBg.cornerRadius = 20F
        iAgreeBtn.background = solidBg
        val iAgreeBtnLabel = iAgreeBtn.findViewById<TextView>(R.id.tiki_sdk_btn_label)
        iAgreeBtnLabel.text = "I agree"
        iAgreeBtnLabel.setTextColor(theme.primaryBackgroundColor)
        iAgreeBtnLabel.typeface = ResourcesCompat.getFont(this, theme.fontMedium)
    }
}