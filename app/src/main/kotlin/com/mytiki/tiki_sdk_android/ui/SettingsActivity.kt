package com.mytiki.tiki_sdk_android.ui

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.mytiki.tiki_sdk_android.R


class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings)
        findViewById<ImageView>(R.id.back_btn).setOnClickListener {
            finish()
        }
    }
}