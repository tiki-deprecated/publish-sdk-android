package com.mytiki.tiki_sdk_android.ui.activities

import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.mytiki.tiki_sdk_android.R
import com.mytiki.tiki_sdk_android.TikiSdk
import io.noties.markwon.Markwon
import io.noties.markwon.ext.tables.TablePlugin

class LearnMoreActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val theme = TikiSdk.theme(this)
        setContentView(R.layout.learn_more)

        val solidBg = GradientDrawable()
        solidBg.shape = GradientDrawable.RECTANGLE
        solidBg.setTint(theme.primaryBackgroundColor)
        findViewById<LinearLayout>(R.id.learn_more_root)
            .background = solidBg

        val learnMoreTextView = findViewById<TextView>(R.id.learn_more_text)
        learnMoreTextView.setTextColor(theme.primaryTextColor)
        val learnMoreText: String =
            assets.open("learn_more.md").bufferedReader().use { it.readText() }
        val markwon = Markwon.builder(this)
            .usePlugin(TablePlugin.create(this))
            .build();
        markwon.setMarkdown(
            learnMoreTextView,
            learnMoreText
        )

        findViewById<ImageView>(R.id.back_btn).setOnClickListener {
            finish()
        }
    }
}