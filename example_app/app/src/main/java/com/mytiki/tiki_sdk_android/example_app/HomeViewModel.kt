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

class HomeViewModel : ViewModel() {

    var tikiSdk: MutableLiveData<TikiSdk?> = MutableLiveData()
    var wallets: MutableLiveData<MutableList<String>> = MutableLiveData(mutableListOf())
    var ownership: MutableLiveData<TikiSdkOwnership?> = MutableLiveData()
    var consent: MutableLiveData<TikiSdkConsent?> = MutableLiveData()
    var toggleStatus: MutableLiveData<Boolean> = MutableLiveData(false)
    var log: MutableLiveData<MutableList<RequestModel>> = MutableLiveData(mutableListOf())
    var url: MutableLiveData<String> = MutableLiveData("https://postman-echo.com/post")
    var httpMethod: MutableLiveData<String> = MutableLiveData("POST")
    var interval: MutableLiveData<Int> = MutableLiveData(15)
    var body: MutableLiveData<String> = MutableLiveData("{\"message\" : \"Hello Tiki!\"}")

    val source: String
        get() {
            return Base64.encodeToString(body.value!!.toByteArray(), DEFAULT)
        }

    private val path: String
        get() = URL(url.value).host!! + URL(url.value).path!!

    suspend fun loadTikiSdk(context: Context, address: String? = null) {
            val publishingId = "e12f5b7b-6b48-4503-8b39-28e4995b5f88"
            val origin = "com.mytiki.tiki_sdk_android.test"
            tikiSdk.value = TikiSdk().init(publishingId, origin, context, address).await()
            if(!wallets.value!!.contains(tikiSdk.value!!.address)) {
                wallets.value!!.apply {
                    val localList = this
                    localList.add(tikiSdk.value!!.address)
                    wallets.postValue(localList)
                }
            }
            getOrAssignOnwership()
    }

    suspend fun getOrAssignOnwership() {
        var localOwnership = tikiSdk.value!!.getOwnership(source)
        if (localOwnership == null) {
            tikiSdk.value!!.assignOwnership(
                source,
                TikiSdkDataTypeEnum.data_stream,
                listOf("generic data"),
                "Data destination created with TIKI SDK Sample App"
            )
            localOwnership = tikiSdk.value!!.getOwnership(source)
        }
        ownership.value = localOwnership
    }

    suspend fun modifyConsent(allow: Boolean) {
            if (ownership.value == null) {
                getOrAssignOnwership()
            }else {
                val use: String = httpMethod.value!!
                val destination = if (allow) {
                    TikiSdkDestination(listOf(path), listOf(use))
                } else {
                    TikiSdkDestination.NONE
                }
                val expiry: Calendar = Calendar.getInstance().apply {
                    this.add(Calendar.YEAR, 10)
                }
                val localConsent: TikiSdkConsent = tikiSdk.value!!.modifyConsent(
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

    fun makeRequest() {
        val onRequestCallback: () -> Unit = {
            viewModelScope.launch(Dispatchers.IO) {
                toggleStatus.postValue(true)
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
                        this.add(RequestModel(
                            "🟢",
                            "${responseCode}: $response"
                        ))
                        log.postValue(this)
                    }
                } else {
                    log.value!!.toMutableList().apply {
                        this.add(RequestModel(
                            "🔴",
                            "${responseCode}: $response"
                        ))
                        log.postValue(this)
                    }
                }
            }
        }
        val onBlockedCallback: (String) -> Unit = {
            log.value!!.toMutableList().apply {
                this.add(
                    RequestModel(
                        "🔴",
                        "Blocked: no consent"
                    )
                )
                log.postValue(this)
                toggleStatus.postValue(false)
            }
        }
        viewModelScope.launch {
            try {
                val use: String = httpMethod.value!!
                tikiSdk.value?.applyConsent(
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