/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.tiki_sdk_android.trail.req

import com.mytiki.tiki_sdk_android.channel.req.Req
import com.mytiki.tiki_sdk_android.trail.Tag

data class ReqTitle(
    val ptr: String,
    val tags: List<Tag>,
    val description: String? = null,
    val origin: String? = null
) : Req() {
    override fun map(): Map<String, Any?> {
        return mapOf(
            "requestId" to requestId,
            "ptr" to ptr,
            "tags" to tags.map { tag -> tag.value },
            "description" to description,
            "origin" to origin
        )
    }
}