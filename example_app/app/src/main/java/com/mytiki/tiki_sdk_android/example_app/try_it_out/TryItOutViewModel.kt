package com.mytiki.tiki_sdk_android.example_app.try_it_out

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mytiki.tiki_sdk_android.*
import com.mytiki.tiki_sdk_android.example_app.stream.Stream
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.InputStreamReader
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL
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

    private var _selectedWalletAddress: MutableLiveData<String?> = MutableLiveData()
    val selectedWalletAddress: LiveData<String?> = _selectedWalletAddress

    private var _log: MutableLiveData<MutableList<TryItOutReq>> = MutableLiveData(mutableListOf())
    val log: LiveData<MutableList<TryItOutReq>>
        get() = _log

    val tikiSdk: TikiSdk?
        get() = wallets.value?.get(selectedWalletAddress.value)

    val ownership: TikiSdkOwnership?
        get() = ownerships.value?.get(selectedWalletAddress.value)

    val consent: TikiSdkConsent?
        get() = consents.value?.get(ownership?.transactionId)

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
    
    fun togglConsent(){
        val path: String = URL(stream.value!!.url).host
        val use: String = stream.value!!.httpMethod
        val destination = if (isConsentGiven.value!!) {
            TikiSdkDestination.NONE
        } else {
            TikiSdkDestination(listOf(path), listOf(use))
        }
        viewModelScope.launch {
            val consent: TikiSdkConsent = tikiSdk!!.modifyConsent(
                ownership!!.transactionId,
                destination,
                "Consent given to echo data in remote server",
                "Test the SDK",
                Calendar.getInstance().apply {
                    add(Calendar.YEAR, 10)
                }.time
            )
            _consents.value!!.toMutableMap().apply{
                this.put(consent.ownershipId, consent)
                _consents.postValue(this)
            }
            isConsentGiven.value.apply {
                _isConsentGiven.postValue(!isConsentGiven.value!!)
            }
        }
    }

    fun makeRequest() {
        var logToAppend: TryItOutReq? = null
        if (tikiSdk == null) {
            logToAppend = TryItOutReq("🔴", "ERROR: Create a Wallet")
            _log.value!!.toMutableList().apply{
                this.add(logToAppend!!)
                _log.postValue(this)
            }
        } else {
            viewModelScope.launch {
                try {
                    val url = URL(stream.value!!.url)
                    val path: String = url.host
                    val use: String = stream.value!!.httpMethod
                    val destination = TikiSdkDestination(listOf(path), listOf(use))
                    tikiSdk!!.applyConsent(stream.value!!.source, destination, {
                        viewModelScope.launch(Dispatchers.IO) {
                            val con = url.openConnection() as HttpURLConnection
                            con.requestMethod = use
                            val postData = stream.value!!.body.toByteArray()
                            con.doOutput = true
                            val wr = DataOutputStream(con.outputStream)
                            wr.write(postData)
                            wr.flush()
                            wr.close()
                            val responseCode = con.responseCode
                            val income = BufferedReader(InputStreamReader(con.inputStream))
                            val response = StringBuilder()
                            var inputLine = income.readLine()
                            while (inputLine != null) {
                                response.append(inputLine)
                                inputLine = income.readLine()
                            }
                            income.close()
                            if (responseCode in 200..299) {
                                logToAppend = TryItOutReq(
                                    "🟢",
                                    "${responseCode}: $response"
                                )
                                _log.value!!.toMutableList().apply{
                                    this.add(logToAppend!!)
                                    _log.postValue(this)
                                }
                            } else {
                                logToAppend = TryItOutReq(
                                    "🔴",
                                    "${responseCode}: $response"
                                )
                                _log.value!!.toMutableList().apply{
                                    this.add(logToAppend!!)
                                    _log.postValue(this)
                                }
                            }
                        }
                    }, {
                        logToAppend = TryItOutReq("🔴", "Blocked: consent required")
                        _log.value!!.toMutableList().apply{
                            this.add(logToAppend!!)
                            _log.postValue(this)
                        }
                    })
                } catch (e: Exception) {
                    logToAppend = TryItOutReq("🔴", e.message.toString())
                    _log.value!!.toMutableList().apply{
                        this.add(logToAppend!!)
                        _log.postValue(this)
                    }
                }
            }
        }
    }
}