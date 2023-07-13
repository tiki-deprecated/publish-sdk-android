/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.tiki_sdk_android.trail.rsp

import com.mytiki.tiki_sdk_android.channel.rsp.Rsp

data class RspId(
    val id: String?,
    override val requestId: String?,
) : Rsp {
    companion object {
        fun from(map: Map<String, Any?>): RspId {
            return RspId(
                map["id"] as String?,
                map["requestId"] as String?
            )
        }
    }
}