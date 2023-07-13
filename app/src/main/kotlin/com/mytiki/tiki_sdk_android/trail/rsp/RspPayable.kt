/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.tiki_sdk_android.trail.rsp

import com.mytiki.tiki_sdk_android.channel.rsp.Rsp
import java.util.Date

data class RspPayable(
    val id: String?,
    val license: RspLicense?,
    val amount: String?,
    val type: String?,
    val description: String?,
    val expiry: Date?,
    val reference: String?,
    override val requestId: String?,
) : Rsp {
    companion object {
        fun from(map: Map<String, Any?>): RspPayable {
            val expiry: Long? = map["expiry"] as Long?
            val license: Map<String, Any?>? = map["license"] as Map<String, Any?>?
            return RspPayable(
                map["id"] as String?,
                if (license != null) RspLicense.from(license) else null,
                map["amount"] as String?,
                map["type"] as String?,
                map["description"] as String?,
                if (expiry != null) Date(expiry) else null,
                map["reference"] as String?,
                map["requestId"] as String?
            )
        }
    }
}