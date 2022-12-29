package com.mytiki.tiki_sdk_android.integration_tests

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.mytiki.tiki_sdk_android.TikiSdk
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    var tikiSdk: TikiSdk? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val scope = MainScope()

        button.setOnClickListener {
            scope.launch {
                tikiSdk = scope.async {
                    TikiSdk().init(
                        "b213d6bd-ccff-45c2-805e-4f0062d4ad5e",
                        "com.mytiki.tiki_sdk_android.integration_tests",
                        applicationContext
                    )
                }.await()
                Log.e("MIKE", "addr: " + tikiSdk?.address)
            }
        }
    }
}