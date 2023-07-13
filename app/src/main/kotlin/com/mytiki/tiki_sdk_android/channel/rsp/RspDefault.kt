/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.tiki_sdk_android.channel.rsp

data class RspDefault(override val requestId: String?) : Rsp {
    companion object {
        fun from(map: Map<String, Any?>): RspDefault {
            return RspDefault(
                map["requestId"] as String?
            )
        }
    }
}