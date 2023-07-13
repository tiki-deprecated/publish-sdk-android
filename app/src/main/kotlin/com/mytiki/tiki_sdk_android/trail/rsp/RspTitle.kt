/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.tiki_sdk_android.trail.rsp

import com.mytiki.tiki_sdk_android.channel.rsp.Rsp
import com.mytiki.tiki_sdk_android.trail.Tag

data class RspTitle(
    val id: String?,
    val hashedPtr: String?,
    val origin: String?,
    val tags: List<Tag>?,
    val description: String?,
    override val requestId: String?,
) : Rsp {
    companion object {
        fun from(map: Map<String, Any?>): RspTitle {
            val tags: List<String>? = map["tags"] as List<String>?
            return RspTitle(
                map["id"] as String?,
                map["hashedPtr"] as String?,
                map["origin"] as String?,
                tags?.map { tag -> Tag.from(tag) },
                map["description"] as String?,
                map["requestId"] as String?
            )
        }
    }
}