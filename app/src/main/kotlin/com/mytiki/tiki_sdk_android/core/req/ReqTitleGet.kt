package com.mytiki.tiki_sdk_android.core.req

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ReqTitleGet(
    val id: String?,
    val origin: String?
)