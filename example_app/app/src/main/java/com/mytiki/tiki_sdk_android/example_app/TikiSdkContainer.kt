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

        suspend fun init (context: Context, address: String? = null) {
            if(address != null && tikiSdkMap.containsKey(address)){
                _currentSdk = tikiSdkMap[address]!!
            }else{
                val deferred = CompletableDeferred<TikiSdk>()
                withContext(Dispatchers.IO) {
                    val apiId = "2b8de004-cbe0-4bd5-bda6-b266d54f5c90"
                    val origin = "com.mytiki.tiki_sdk_android.test"
                    _currentSdk = TikiSdk().init(apiId, origin, context, address).await()
                    tikiSdkMap[_currentSdk!!.address] = _currentSdk!!
                    deferred.complete(_currentSdk!!)
                }
                _currentSdk = deferred.await()
            }
        }
    }
}