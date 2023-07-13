/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.tiki_sdk_android.trail.rsp

import com.mytiki.tiki_sdk_android.channel.rsp.Rsp

data class RspPayables(
    val payables: List<RspPayable>?,
    override val requestId: String?,
) : Rsp {
    companion object {
        fun from(map: Map<String, Any?>): RspPayables {
            val payables: List<Map<String, Any?>>? = map["payables"] as List<Map<String, Any?>>?
            return RspPayables(
                payables?.map { payable -> RspPayable.from(payable) },
                map["requestId"] as String?
            )
        }
    }
}