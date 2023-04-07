package com.mytiki.tiki_sdk_android.ui

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.mytiki.tiki_sdk_android.R


class TermsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.terms)
        findViewById<ConstraintLayout>(R.id.color_btn).setOnClickListener {
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