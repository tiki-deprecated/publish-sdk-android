/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.tiki_sdk_android.trail.rsp

import com.mytiki.tiki_sdk_android.channel.rsp.Rsp

data class RspReceipts(
    val receipts: List<RspReceipt>?,
    override val requestId: String?,
) : Rsp {
    companion object {
        fun from(map: Map<String, Any?>): RspReceipts {
            val receipts: List<Map<String, Any?>>? = map["receipts"] as List<Map<String, Any?>>?
            return RspReceipts(
                receipts?.map { receipt -> RspReceipt.from(receipt) },
                map["requestId"] as String?
            )
        }
    }
}