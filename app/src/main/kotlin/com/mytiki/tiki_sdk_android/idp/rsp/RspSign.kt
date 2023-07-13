/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.tiki_sdk_android.idp.rsp

import android.util.Base64
import com.mytiki.tiki_sdk_android.channel.rsp.Rsp

data class RspSign(
    val signature: ByteArray?,
    override val requestId: String?
) : Rsp {
    companion object {
        fun from(map: Map<String, Any?>): RspSign {
            val b64Sig: String? = map["signature"] as String?
            return RspSign(
                if (b64Sig == null) null else Base64.decode(b64Sig, Base64.DEFAULT),
                map["requestId"] as String?,
            )
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as RspSign

        if (signature != null) {
            if (other.signature == null) return false
            if (!signature.contentEquals(other.signature)) return false
        } else if (other.signature != null) return false
        if (requestId != other.requestId) return false

        return true
    }

    override fun hashCode(): Int {
        var result = signature?.contentHashCode() ?: 0
        result = 31 * result + (requestId?.hashCode() ?: 0)
        return result
    }
}