/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.tiki_sdk_android.idp.rsp

import com.mytiki.tiki_sdk_android.channel.rsp.Rsp

data class RspVerify(
    val isVerified: Boolean,
    override val requestId: String?
) : Rsp {
    companion object {
        fun from(map: Map<String, Any?>): RspVerify {
            return RspVerify(
                map["isVerified"] as Boolean? ?: false,
                map["requestId"] as String?,
            )
        }
    }
}