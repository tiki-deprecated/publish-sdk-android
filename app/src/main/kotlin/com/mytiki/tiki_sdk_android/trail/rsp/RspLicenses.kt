/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.tiki_sdk_android.trail.rsp

import com.mytiki.tiki_sdk_android.channel.rsp.Rsp

data class RspLicenses(
    val licenses: List<RspLicense>?,
    override val requestId: String?,
) : Rsp {
    companion object {
        fun from(map: Map<String, Any?>): RspLicenses {
            val licenses: List<Map<String, Any?>>? = map["licenses"] as List<Map<String, Any?>>?
            return RspLicenses(
                licenses?.map { license -> RspLicense.from(license) },
                map["requestId"] as String?
            )
        }
    }
}