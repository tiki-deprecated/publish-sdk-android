package com.mytiki.tiki_sdk_android.core.rsp

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RspInit(
    val address: String
)