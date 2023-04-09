/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */
package com.mytiki.tiki_sdk_android.core.req

import com.mytiki.tiki_sdk_android.TitleTag
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ReqTitle(
    val ptr: String?,
    val tags: List<TitleTag> = emptyList(),
    val description: String?,
    val origin: String?
) {
    fun toJson(): String {
        val builder = StringBuilder()
        builder.append("{")
        builder.append("\"ptr\":").append("\"").append(ptr).append("\"").append(",")
        builder.append("\"tags\":").append("[")
        for (i in tags.indices) {
            builder.append(tags[i].toJson())
            if (i != tags.size - 1) {
                builder.append(",")
            }
        }
        builder.append("]").append(",")
        builder.append("\"description\":").append("\"").append(description).append("\"").append(",")
        builder.append("\"origin\":").append("\"").append(origin).append("\"").append(",")
        builder.append("}")
        return builder.toString()
    }
}