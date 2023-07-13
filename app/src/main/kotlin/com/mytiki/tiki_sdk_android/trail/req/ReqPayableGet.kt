/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.tiki_sdk_android.trail.req

import com.mytiki.tiki_sdk_android.channel.req.Req

data class ReqPayableGet(
    val id: String
) : Req() {
    override fun map(): Map<String, Any?> {
        return mapOf(
            "requestId" to requestId,
            "id" to id
        )
    }
}