package com.mytiki.tiki_sdk_android.example_app.stream.list

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

class StreamListViewModel : ViewModel() {

    private val _streams: MutableLiveData<ArrayList<Stream>> = MutableLiveData(arrayListOf())
    val streams: LiveData<ArrayList<Stream>>
        get() = _streams
    private var _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    fun createStream(tikiSdk: TikiSdk?, data: String) {
        if (tikiSdk != null){
            _isLoading.value = true
            var stream: Stream? = null
            viewModelScope.launch {
                val source: String = UUID.randomUUID().toString()
                tikiSdk.assignOwnership(
                    source,
                    TikiSdkDataTypeEnum.data_stream,
                    listOf("generic data", "Data stream created with TIKI SDK Sample App")
                )
                stream = Stream(source, data)
            }.invokeOnCompletion {
                if (it == null) {
                    _streams.value!!.add(stream!!).apply {
                        _streams.postValue(_streams.value)
                    }
                } else {
                    throw it
                }
                _isLoading.value = false
            }
        }
    }
}
