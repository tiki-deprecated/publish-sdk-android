package com.mytiki.tiki_sdk_android.example_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mytiki.tiki_sdk_android.TikiSdk
import java.util.*

private const val publishingId = "e12f5b7b-6b48-4503-8b39-28e4995b5f88"
private val id = UUID.randomUUID().toString()

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        TikiSdk.init(this, publishingId, id) {}
    }
}