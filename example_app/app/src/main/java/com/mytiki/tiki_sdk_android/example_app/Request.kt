package com.mytiki.tiki_sdk_android.example_app

import java.text.DateFormat
import java.text.DateFormat.getTimeInstance
import java.util.*

data class Request(val icon: String, val message: String) {
    private val dateFormat: DateFormat = getTimeInstance()
    val timestamp: String = dateFormat.format(Date())
}