/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.tiki_sdk_android.idp.req

import android.util.Base64
import com.mytiki.tiki_sdk_android.channel.req.Req

data class ReqImport(
    val keyId: String,
    val key: ByteArray,
    val public: Boolean = false,
) : Req() {
    override fun map(): Map<String, Any?> {
        return mapOf(
            "requestId" to requestId,
            "keyId" to keyId,
            "key" to Base64.encodeToString(key, Base64.DEFAULT),
            "public" to public
        )
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ReqImport

        if (keyId != other.keyId) return false
        if (!key.contentEquals(other.key)) return false
        if (public != other.public) return false

        return true
    }

    override fun hashCode(): Int {
        var result = keyId.hashCode()
        result = 31 * result + key.contentHashCode()
        result = 31 * result + public.hashCode()
        return result
    }
}