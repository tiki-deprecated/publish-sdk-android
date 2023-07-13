/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.tiki_sdk_android.trail.rsp

import com.mytiki.tiki_sdk_android.channel.rsp.Rsp

data class RspGuard(
    val success: Boolean = false,
    val reason: String?,
    override val requestId: String?,
) : Rsp {
    companion object {
        fun from(map: Map<String, Any?>): RspGuard {
            return RspGuard(
                map["success"] as Boolean? ?: false,
                map["reason"] as String?,
                map["requestId"] as String?
            )
        }
    }
}