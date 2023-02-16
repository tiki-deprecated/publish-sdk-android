package com.mytiki.tiki_sdk_android.example_app

import android.content.Context
import androidx.lifecycle.*
import com.mytiki.tiki_sdk_android.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import android.util.Base64
import android.util.Base64.DEFAULT
import java.util.Calendar

class HomeViewModel(var tikiSdk: TikiSdk) : ViewModel() {

    var wallets: MutableLiveData<MutableList<String>> = MutableLiveData(mutableListOf(tikiSdk.address))
    val ownership: MutableLiveData<TikiSdkOwnership?> = MutableLiveData()
    val consent: MutableLiveData<TikiSdkConsent?> = MutableLiveData()
    var toggleStatus: MutableLiveData<Boolean> = MutableLiveData(false)
    var log: MutableLiveData<MutableList<Request>> = MutableLiveData(mutableListOf())
    var url: MutableLiveData<String> = MutableLiveData("https://postman-echo.com/post")
    var httpMethod: MutableLiveData<String> = MutableLiveData("POST")
    var interval: MutableLiveData<Int> = MutableLiveData(15)
    var body: MutableLiveData<String> = MutableLiveData("{\"message\" : \"Hello Tiki!\"}")

    private val source: String
        get() {
            return Base64.encodeToString(body.value!!.toByteArray(), DEFAULT)
        }

    private val path: String
        get() = URL(url.value).host!! + URL(url.value).path!!

    fun loadTikiSdk(context: Context, address: String? = null) {
        viewModelScope.launch {
            val publishingId = "2b8de004-cbe0-4bd5-bda6-b266d54f5c90"
            val origin = "com.mytiki.tiki_sdk_android.test"
            val tikiSdk = TikiSdk().init(publishingId, origin, context, address).await()
            wallets.value!!.add(tikiSdk.address).apply {
                wallets.postValue(wallets.value)
            }
            getOrAssignOnwership()
        }
    }

    fun getOrAssignOnwership() {
        viewModelScope.launch {
            var localOwnership = tikiSdk.getOwnership(source)
            if (localOwnership == null) {
                val ownershipId: String = tikiSdk.assignOwnership(
                    source,
                    TikiSdkDataTypeEnum.data_stream,
                    listOf("generic data"),
                    "Data destination created with TIKI SDK Sample App"
                )
                localOwnership = tikiSdk.getOwnership(source)
            }
            ownership.value = localOwnership
        }
    }

    fun modifyConsent(allow: Boolean) {
        viewModelScope.launch {
            if (ownership.value == null) {
                getOrAssignOnwership()
            }else {
                val use: String = httpMethod.value!!
                val destination = if (allow) {
                    TikiSdkDestination.NONE
                } else {
                    TikiSdkDestination(listOf(path), listOf(use))
                }
                val expiry: Calendar = Calendar.getInstance().apply {
                    this.add(Calendar.YEAR, 10)
                }
                val localConsent: TikiSdkConsent = tikiSdk.modifyConsent(
                    ownership.value!!.transactionId,
                    destination,
                    "Consent given to echo data in remote server",
                    "Test the SDK",
                    expiry.time
                )
                toggleStatus.value = allow
                consent.value = localConsent
            }
        }
    }

    fun makeRequest() {
        val onRequestCallback: () -> Unit = {
            viewModelScope.launch(Dispatchers.IO) {
                val con = URL(url.value!!).openConnection() as HttpURLConnection
                con.requestMethod = httpMethod.value
                val postData =
                    this@HomeViewModel.body.value!!.toByteArray()
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
                    log.value!!.toMutableList().apply {
                        this.add(Request(
                            "🟢",
                            "${responseCode}: $response"
                        ))
                        log.postValue(this)
                    }
                } else {
                    log.value!!.toMutableList().apply {
                        this.add(Request(
                            "🔴",
                            "${responseCode}: $response"
                        ))
                        log.postValue(this)
                    }
                }
            }
        }
        val onBlockedCallback: (String) -> Unit = {
            Request(
                "🔴",
                it
            )
        }
        viewModelScope.launch {
            try {
                val use: String = httpMethod.value!!
                tikiSdk.applyConsent(
                    this@HomeViewModel.source,
                    TikiSdkDestination(listOf(path), listOf(use)),
                    onRequestCallback,
                    onBlockedCallback)
            } catch (e: Exception) {
                onBlockedCallback(e.toString())
            }
        }
    }
}