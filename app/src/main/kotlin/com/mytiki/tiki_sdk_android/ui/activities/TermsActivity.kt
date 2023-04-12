package com.mytiki.tiki_sdk_android.ui.activities

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import com.mytiki.tiki_sdk_android.R
import com.mytiki.tiki_sdk_android.TikiSdk
import io.noties.markwon.Markwon
import io.noties.markwon.ext.tables.TablePlugin

class TermsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.terms)
        val markwon = Markwon.builder(this)
            .usePlugin(TablePlugin.create(this))
            .build();
        markwon.setMarkdown(findViewById(R.id.terms_text), TikiSdk.offers.values.first().terms);
        findViewById<RelativeLayout>(R.id.tiki_sdk_btn).setOnClickListener {
            val returnIntent = Intent()
            setResult(RESULT_OK, returnIntent)
            finish()
        }
        findViewById<ImageView>(R.id.back_btn).setOnClickListener {
            val returnIntent = Intent()
            setResult(RESULT_CANCELED, returnIntent)
            finish()
        }
    }
}