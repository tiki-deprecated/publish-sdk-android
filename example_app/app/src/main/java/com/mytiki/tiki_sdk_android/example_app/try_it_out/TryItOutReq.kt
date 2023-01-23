package com.mytiki.tiki_sdk_android.example_app.try_it_out

import java.text.DateFormat
import java.text.DateFormat.getTimeInstance
import java.util.*

data class TryItOutReq(val req: String){
    private val dateFormat: DateFormat = getTimeInstance()
    val timestamp: String = dateFormat.format(Date())
}