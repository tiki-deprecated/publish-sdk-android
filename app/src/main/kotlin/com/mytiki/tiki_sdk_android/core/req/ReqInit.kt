/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */
package com.mytiki.tiki_sdk_android.core.req


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ReqInit(
    @Json(name = "publishingId") val publishingId: String,
    @Json(name = "id") val id: String,
    @Json(name = "origin") val origin: String,
)
