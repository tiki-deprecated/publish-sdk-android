/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.tiki_sdk_android.trail.req

import com.mytiki.tiki_sdk_android.channel.req.Req

data class ReqReceipt(
    val payableId: String,
    val amount: String,
    val description: String? = null,
    val reference: String? = null
) : Req() {
    override fun map(): Map<String, Any?> {
        return mapOf(
            "requestId" to requestId,
            "payableId" to payableId,
            "amount" to amount,
            "description" to description,
            "reference" to reference
        )
    }
}