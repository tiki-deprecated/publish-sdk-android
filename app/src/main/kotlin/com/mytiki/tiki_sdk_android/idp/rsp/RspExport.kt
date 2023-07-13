/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.tiki_sdk_android.idp.rsp

import com.mytiki.tiki_sdk_android.channel.rsp.Rsp

data class RspExport(
    val key: String?,
    override val requestId: String?
) : Rsp {
    companion object {
        fun from(map: Map<String, Any?>): RspExport {
            return RspExport(
                map["key"] as String?,
                map["requestId"] as String?,
            )
        }
    }
}