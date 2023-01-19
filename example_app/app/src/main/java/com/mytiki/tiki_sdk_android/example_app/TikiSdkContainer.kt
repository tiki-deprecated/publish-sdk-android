package com.mytiki.tiki_sdk_android.example_app

import android.content.Context
import com.mytiki.tiki_sdk_android.TikiSdk
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TikiSdkContainer {
    companion object{
        private val tikiSdkMap: MutableMap<String, TikiSdk> = mutableMapOf()
        private var _currentSdk: TikiSdk? = null

        val currentSdk: TikiSdk
            get() = _currentSdk!!

        fun switch(address: String): TikiSdk{
            _currentSdk = tikiSdkMap[address]!!
            return currentSdk
        }


    }
}