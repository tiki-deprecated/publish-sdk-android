/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */
package com.mytiki.tiki_sdk_android.trail

import com.mytiki.tiki_sdk_android.trail.rsp.RspTitle

data class TitleRecord(
    val id: String,
    val hashedPtr: String,
    val tags: List<Tag>,
    val description: String?,
    val origin: String?,
) {
    companion object {
        fun from(rsp: RspTitle): TitleRecord {
            return TitleRecord(
                rsp.id!!,
                rsp.hashedPtr!!,
                rsp.tags ?: emptyList(),
                rsp.description,
                rsp.origin
            )
        }
    }
}
