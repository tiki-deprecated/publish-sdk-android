/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.tiki_sdk_android.idp.req

import android.util.Base64
import com.mytiki.tiki_sdk_android.channel.req.Req

data class ReqSign(
    val keyId: String,
    val message: ByteArray,
) : Req() {
    override fun map(): Map<String, Any?> {
        return mapOf(
            "requestId" to requestId,
            "keyId" to keyId,
            "message" to Base64.encodeToString(message, Base64.DEFAULT)
        )
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ReqSign

        if (keyId != other.keyId) return false
        if (!message.contentEquals(other.message)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = keyId.hashCode()
        result = 31 * result + message.contentHashCode()
        return result
    }
}