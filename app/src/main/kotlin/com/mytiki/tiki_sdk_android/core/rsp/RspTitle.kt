package com.mytiki.tiki_sdk_android.core.rsp

import TitleRecord
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RspTitle(
    val title: TitleRecord?
)