/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.tiki_sdk_android.trail.rsp

import com.mytiki.tiki_sdk_android.channel.rsp.Rsp

data class RspReceipt(
    val id: String?,
    val payable: RspPayable?,
    val amount: String?,
    val description: String?,
    val reference: String?,
    override val requestId: String?,
) : Rsp {
    companion object {
        fun from(map: Map<String, Any?>): RspReceipt {
            val payable: Map<String, Any?>? = map["payable"] as Map<String, Any?>?
            return RspReceipt(
                map["id"] as String?,
                if (payable != null) RspPayable.from(payable) else null,
                map["amount"] as String?,
                map["description"] as String?,
                map["reference"] as String?,
                map["requestId"] as String?
            )
        }
    }
}