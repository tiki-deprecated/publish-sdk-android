package com.mytiki.tiki_sdk_android.core.req

import com.mytiki.tiki_sdk_android.TitleTag
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ReqTitle(
    val ptr: String?,
    val tags: List<TitleTag> = emptyList(),
    val description: String?,
    val origin: String?
)
