/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */
package com.mytiki.tiki_sdk_android.core.req

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ReqLicenseAll(
    val ptr: String?,
    val origin: String?
)
