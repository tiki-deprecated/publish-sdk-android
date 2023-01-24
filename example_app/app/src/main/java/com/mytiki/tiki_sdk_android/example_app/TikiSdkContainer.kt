package com.mytiki.tiki_sdk_android.example_app

import com.mytiki.tiki_sdk_android.TikiSdk

class TikiSdkContainer {
    companion object {
        private val tikiSdkMap: MutableMap<String, TikiSdk> = mutableMapOf()
        private var _currentSdk: TikiSdk? = null

        val currentSdk: TikiSdk
            get() = _currentSdk!!

        fun switch(address: String): TikiSdk {
            _currentSdk = tikiSdkMap[address]!!
            return currentSdk
        }


    }
}