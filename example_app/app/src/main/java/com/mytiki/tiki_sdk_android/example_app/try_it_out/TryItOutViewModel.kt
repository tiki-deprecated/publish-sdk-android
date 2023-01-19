package com.mytiki.tiki_sdk_android.example_app.try_it_out

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mytiki.tiki_sdk_android.TikiSdk
import com.mytiki.tiki_sdk_android.TikiSdkConsent
import com.mytiki.tiki_sdk_android.TikiSdkOwnership
import com.mytiki.tiki_sdk_android.example_app.stream.Stream
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

class TryItOutViewModel : ViewModel() {

    private var _wallets: MutableLiveData<MutableMap<String, TikiSdk>> = MutableLiveData(mutableMapOf())
    val wallets: LiveData<MutableMap<String,TikiSdk>>
        get() = _wallets

    private var _ownerships: MutableLiveData<MutableMap<String, TikiSdkOwnership>> = MutableLiveData(mutableMapOf())
    val ownerships: LiveData<MutableMap<String, TikiSdkOwnership>>
        get() = _ownerships

    private var _consents: MutableLiveData<MutableMap<String, TikiSdkConsent>> = MutableLiveData(mutableMapOf())
    val consents: LiveData<MutableMap<String, TikiSdkConsent>>
        get() = _consents

    private var _stream: MutableLiveData<Stream> = MutableLiveData(Stream(UUID.randomUUID().toString()))
    val stream: LiveData<Stream> = _stream

    private var _isConsentGiven: MutableLiveData<Boolean> = MutableLiveData(false)
    val isConsentGiven: LiveData<Boolean> = _isConsentGiven

    private var _selectedWalletAddress: MutableLiveData<String> = MutableLiveData("")
    val selectedWalletAddress: LiveData<String> = _selectedWalletAddress

    val tikiSdk: TikiSdk?
        get() = wallets.value?.get(selectedWalletAddress.value)

    val ownership: TikiSdkOwnership?
        get() = ownerships.value?.get(selectedWalletAddress.value)

    val consent: TikiSdkConsent?
        get() = consents.value?.get(ownership?.transactionId)

    suspend fun loadTikiSdk (context: Context, address: String? = null) {
        if(address != null && wallets.value?.containsKey(address) == true){
            _selectedWalletAddress.postValue(address!!)
        }else{
            val deferred = CompletableDeferred<Boolean>()
            withContext(Dispatchers.IO) {
                val apiId = "2b8de004-cbe0-4bd5-bda6-b266d54f5c90"
                val origin = "com.mytiki.tiki_sdk_android.test"
                val tikiSdk = TikiSdk().init(apiId, origin, context, address).await()
                _wallets.value!!.put(tikiSdk.address, tikiSdk).apply {
                    _wallets.postValue(_wallets.value)
                }
                deferred.complete(true)
            }
        }
    }
}