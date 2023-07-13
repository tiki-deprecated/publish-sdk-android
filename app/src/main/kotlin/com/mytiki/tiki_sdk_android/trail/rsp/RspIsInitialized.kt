/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.tiki_sdk_android.trail.rsp

import com.mytiki.tiki_sdk_android.channel.rsp.Rsp

data class RspIsInitialized(
    val isInitialized: Boolean,
    override val requestId: String?
) : Rsp {
    companion object {
        fun from(map: Map<String, Any?>): RspIsInitialized {
            return RspIsInitialized(
                map["isInitialized"] as Boolean? ?: false,
                map["requestId"] as String?
            )
        }
    }
}