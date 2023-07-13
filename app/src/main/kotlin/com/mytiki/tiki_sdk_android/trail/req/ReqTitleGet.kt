/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.tiki_sdk_android.trail.req

import com.mytiki.tiki_sdk_android.channel.req.Req

data class ReqTitleGet(
    val ptr: String,
    val origin: String? = null
) : Req() {
    override fun map(): Map<String, Any?> {
        return mapOf(
            "requestId" to requestId,
            "ptr" to ptr,
            "origin" to origin
        )
    }
}