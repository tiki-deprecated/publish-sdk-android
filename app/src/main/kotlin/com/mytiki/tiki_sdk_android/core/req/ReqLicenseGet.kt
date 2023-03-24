package com.mytiki.tiki_sdk_android.core.req

import com.squareup.moshi.JsonClass
import java.util.*

@JsonClass(generateAdapter = true)
data class ReqLicenseGet(
    val id: String?,
    val origin: String?
)

