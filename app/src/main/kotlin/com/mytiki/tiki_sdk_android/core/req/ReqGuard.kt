/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.tiki_sdk_android.core.req

import com.mytiki.tiki_sdk_android.LicenseUsecase
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ReqGuard(
    @Json(name = "ptr") var ptr: String? = null,
    @Json(name = "usecases") var usecases: List<LicenseUsecase> = emptyList(),
    @Json(name = "destinations") var destinations: List<String>? = null,
    @Json(name = "origin") var origin: String? = null
)
