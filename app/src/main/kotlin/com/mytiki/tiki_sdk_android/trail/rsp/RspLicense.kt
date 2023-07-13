/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.tiki_sdk_android.trail.rsp

import com.mytiki.tiki_sdk_android.channel.rsp.Rsp
import com.mytiki.tiki_sdk_android.trail.Use
import java.util.Date

data class RspLicense(
    val id: String?,
    val title: RspTitle?,
    val uses: List<Use>?,
    val terms: String?,
    val description: String?,
    val expiry: Date?,
    override val requestId: String?,
) : Rsp {
    companion object {
        fun from(map: Map<String, Any?>): RspLicense {
            val expiry: Long? = map["expiry"] as Long?
            val title: Map<String, Any?>? = map["title"] as Map<String, Any?>?
            val uses: List<Map<String, Any?>>? = map["uses"] as List<Map<String, Any?>>?
            return RspLicense(
                map["id"] as String?,
                if (title != null) RspTitle.from(title) else null,
                uses?.map { use -> Use.from(use) },
                map["terms"] as String?,
                map["description"] as String?,
                if (expiry != null) Date(expiry) else null,
                map["requestId"] as String?
            )
        }
    }
}