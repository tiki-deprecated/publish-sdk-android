/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.tiki_sdk_android.idp.req

import com.mytiki.tiki_sdk_android.channel.req.Req

data class ReqExport(
    val keyId: String,
    val public: Boolean = false,
) : Req() {
    override fun map(): Map<String, Any?> {
        return mapOf(
            "requestId" to requestId,
            "keyId" to keyId,
            "public" to public
        )
    }
}