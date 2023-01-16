package com.mytiki.tiki_sdk_android.example_app.wallet

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mytiki.tiki_sdk_android.TikiSdk
import com.mytiki.tiki_sdk_android.example_app.TikiSdkContainer
import kotlinx.coroutines.launch

class WalletListViewModel : ViewModel() {

    private val _wallets: MutableLiveData<ArrayList<String>> = MutableLiveData(arrayListOf())
    val wallets: LiveData<ArrayList<String>>
        get() = _wallets
    private var _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    fun createWallet(context : Context) {
        _isLoading.value = true
        var tikiSdk : TikiSdk? = null
        viewModelScope.launch {
            TikiSdkContainer.init(context)
            tikiSdk = TikiSdkContainer.currentSdk
        }.invokeOnCompletion {
            if(it == null){
                _wallets.value!!.add(tikiSdk!!.address).apply{
                    _wallets.postValue(_wallets.value)
                }
            }else{
                throw it
            }
            _isLoading.value = false
        }
    }
}
