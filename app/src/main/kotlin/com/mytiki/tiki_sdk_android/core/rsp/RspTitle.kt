package com.mytiki.tiki_sdk_android.core.rsp

import com.mytiki.tiki_sdk_android.TitleRecord
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RspTitle(
    val title: TitleRecord?
)