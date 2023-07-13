/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.tiki_sdk_android.idp.rsp

import com.mytiki.tiki_sdk_android.channel.rsp.Rsp
import java.util.Date

/*
  final String accessToken;
  final String tokenType;
  final int expires;
  final String? refreshToken;
  final List<String>? scope;
 */

data class RspToken(
    val accessToken: String?,
    val tokenType: String?,
    val expires: Date?,
    val refreshToken: String?,
    val scope: List<String>?,
    override val requestId: String?
) : Rsp {
    companion object {
        fun from(map: Map<String, Any?>): RspToken {
            val expires: Long? = map["expires"] as Long?
            return RspToken(
                map["accessToken"] as String?,
                map["tokenType"] as String?,
                if (expires != null) Date(expires) else null,
                map["refreshToken"] as String?,
                map["scope"] as List<String>?,
                map["requestId"] as String?,
            )
        }
    }
}