package com.mytiki.tiki_sdk_android.core.rsp
import com.squareup.moshi.JsonClass
import java.util.*

@JsonClass(generateAdapter = true)
data class RspInit(
    val address: String
)