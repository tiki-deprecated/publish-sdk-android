/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.tiki_sdk_android.trail.req

import com.mytiki.tiki_sdk_android.channel.req.Req

data class ReqPayableAll(
    val licenseId: String
) : Req() {
    override fun map(): Map<String, Any?> {
        return mapOf(
            "requestId" to requestId,
            "licenseId" to licenseId
        )
    }
}