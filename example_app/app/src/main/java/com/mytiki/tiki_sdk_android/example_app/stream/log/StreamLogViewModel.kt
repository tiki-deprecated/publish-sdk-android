package com.mytiki.tiki_sdk_android.example_app.stream.log

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mytiki.tiki_sdk_android.TikiSdk
import com.mytiki.tiki_sdk_android.TikiSdkDataTypeEnum
import com.mytiki.tiki_sdk_android.example_app.stream.Stream
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

class StreamLogViewModel : ViewModel() {

    private val _logs: MutableLiveData<ArrayList<Stream>> = MutableLiveData(arrayListOf())
    val logs: LiveData<ArrayList<Stream>>
        get() = _logs
    private var _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    fun openNft(ownershipId: String) {

    }

    fun sendRequest(stream: Stream){

    }
}
