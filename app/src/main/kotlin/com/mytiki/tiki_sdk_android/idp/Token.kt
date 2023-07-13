/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.tiki_sdk_android.idp

import com.mytiki.tiki_sdk_android.idp.rsp.RspToken
import java.util.Date

data class Token(
    val accessToken: String,
    val tokenType: String,
    val expires: Date?,
    val refreshToken: String?,
    val scope: List<String>?,
) {
    companion object {
        fun from(rsp: RspToken): Token {
            return Token(
                rsp.accessToken!!,
                rsp.tokenType!!,
                rsp.expires,
                rsp.refreshToken,
                rsp.scope
            )
        }
    }
}