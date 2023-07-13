/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.tiki_sdk_android.trail.req

import com.mytiki.tiki_sdk_android.channel.req.Req
import com.mytiki.tiki_sdk_android.trail.Use
import java.util.Date

data class ReqLicense(
    val titleId: String,
    val uses: List<Use>,
    val terms: String,
    val expiry: Date? = null,
    val description: String? = null
) : Req() {
    override fun map(): Map<String, Any?> {
        return mapOf(
            "requestId" to requestId,
            "titleId" to titleId,
            "uses" to uses.map { use -> use.map() },
            "terms" to terms,
            "expiry" to expiry?.time,
            "description" to description
        )
    }
}