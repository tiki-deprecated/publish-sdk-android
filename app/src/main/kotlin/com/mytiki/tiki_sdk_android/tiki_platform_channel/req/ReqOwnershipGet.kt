/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */
package com.mytiki.tiki_sdk_android.tiki_platform_channel.req

import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi

/**
 * The request for the `getOwnership` method call in the Platform Channel.
 *
 * @property requestId
 * @property source
 * @property origin
 * @constructor Create empty Req ownership get
 */
@JsonClass(generateAdapter = true)
data class ReqOwnershipGet(
    override val requestId: String,
    val source: String,
    val origin: String? = null
) : Req(requestId) {

    fun toJson(): String {
        return Moshi.Builder().build().adapter(ReqOwnershipGet::class.java).toJson(this)
    }

}


