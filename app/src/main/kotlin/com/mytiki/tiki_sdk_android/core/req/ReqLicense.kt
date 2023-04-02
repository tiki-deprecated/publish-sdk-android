package com.mytiki.tiki_sdk_android.core.req

import com.mytiki.tiki_sdk_android.LicenseUse
import com.mytiki.tiki_sdk_android.TitleTag
import com.squareup.moshi.Json
import java.util.*

data class ReqLicense(
    @Json(name = "ptr") val ptr: String?,
    @Json(name = "terms") val terms: String?,
    @Json(name = "titleDescription") val titleDescription: String?,
    @Json(name = "licenseDescription") val licenseDescription: String?,
    @Json(name = "uses") val uses: List<LicenseUse> = emptyList(),
    @Json(name = "tags") val tags: List<TitleTag> = emptyList(),
    @Json(name = "expiry") val expiry: Date?,
    @Json(name = "origin") val origin: String?
)