/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.tiki_sdk_android.idp.req

import android.util.Base64
import com.mytiki.tiki_sdk_android.channel.req.Req

data class ReqVerify(
    val keyId: String,
    val message: ByteArray,
    val signature: ByteArray
) : Req() {
    override fun map(): Map<String, Any?> {
        return mapOf(
            "requestId" to requestId,
            "keyId" to keyId,
            "message" to Base64.encodeToString(message, Base64.DEFAULT),
            "signature" to Base64.encodeToString(signature, Base64.DEFAULT)
        )
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ReqVerify

        if (keyId != other.keyId) return false
        if (!message.contentEquals(other.message)) return false
        if (!signature.contentEquals(other.signature)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = keyId.hashCode()
        result = 31 * result + message.contentHashCode()
        result = 31 * result + signature.contentHashCode()
        return result
    }
}