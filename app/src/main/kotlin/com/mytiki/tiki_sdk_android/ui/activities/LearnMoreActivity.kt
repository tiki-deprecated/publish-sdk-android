package com.mytiki.tiki_sdk_android.ui.activities

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.mytiki.tiki_sdk_android.R

class LearnMoreActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.learn_more)
        findViewById<ImageView>(R.id.back_btn).setOnClickListener {
            finish()
        }
    }
}