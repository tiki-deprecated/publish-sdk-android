package com.mytiki.tiki_sdk_android.ui.activities

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import com.mytiki.tiki_sdk_android.R

class TermsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.terms)
        findViewById<RelativeLayout>(R.id.color_btn).setOnClickListener {
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