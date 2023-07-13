/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.tiki_sdk_android.trail.req

import com.mytiki.tiki_sdk_android.channel.req.Req
import java.util.Date

data class ReqPayable(
    val licenseId: String,
    val amount: String,
    val type: String,
    val expiry: Date? = null,
    val description: String? = null,
    val reference: String? = null
) : Req() {
    override fun map(): Map<String, Any?> {
        return mapOf(
            "requestId" to requestId,
            "licenseId" to licenseId,
            "amount" to amount,
            "type" to type,
            "expiry" to expiry?.time,
            "description" to description,
            "reference" to reference
        )
    }
}