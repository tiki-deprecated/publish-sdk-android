package com.mytiki.tiki_sdk_android.example_app.try_it_out

import android.app.Activity
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.Navigation
import com.mytiki.tiki_sdk_android.*
import com.mytiki.tiki_sdk_android.example_app.stream.Stream
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL
import java.util.*
import kotlin.collections.ArrayList

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

    private var _selectedWalletAddress: MutableLiveData<String?> = MutableLiveData()
    val selectedWalletAddress: LiveData<String?> = _selectedWalletAddress

    val tikiSdk: TikiSdk?
        get() = wallets.value?.get(selectedWalletAddress.value)

    val ownership: TikiSdkOwnership?
        get() = ownerships.value?.get(selectedWalletAddress.value)

    val consent: TikiSdkConsent?
        get() = consents.value?.get(ownership?.transactionId)

    private val _requests: MutableLiveData<MutableList<TryItOutReq>> = MutableLiveData(mutableListOf())
    val requests: LiveData<MutableList<TryItOutReq>> = _requests

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    fun loadTikiSdk (context: Context, address: String? = null) {
        if(address != null && wallets.value?.containsKey(address) == true){
            _selectedWalletAddress.postValue(address)
        }else {
            viewModelScope.launch {
                val apiId = "2b8de004-cbe0-4bd5-bda6-b266d54f5c90"
                val origin = "com.mytiki.tiki_sdk_android.test"
                val tikiSdk = TikiSdk().init(apiId, origin, context, address).await()
                _wallets.value!!.put(tikiSdk.address, tikiSdk).apply {
                    _wallets.postValue(_wallets.value)
                }
                _selectedWalletAddress.postValue(tikiSdk.address)
                tikiSdk.assignOwnership(stream.value!!.source, TikiSdkDataTypeEnum.data_stream, listOf("generic data"), "Data stream created with TIKI SDK Sample App")
                val ownership = tikiSdk.getOwnership(stream.value!!.source)
                _ownerships.value!!.toMutableMap().apply{
                    this.put(tikiSdk.address, ownership!!)
                    _ownerships.postValue(this)
                }
                val path: String = URL(stream.value?.url).host!!
                val use: String = stream.value!!.httpMethod
                val destination = TikiSdkDestination(listOf(path), listOf(use))
                val expiry: Calendar = Calendar.getInstance().apply {
                    this.add(Calendar.YEAR, 10)
                }
                val consent: TikiSdkConsent = tikiSdk.modifyConsent(ownership!!.transactionId, destination, "Consent given to echo data in remote server", "Test the SDK", expiry.time)
                _consents.value!!.toMutableMap().apply{
                    this.put(ownership.transactionId, consent)
                    _consents.postValue(this)
                }
                _isConsentGiven.value = true
                _isLoading.value = false
            }
        }
    }


}