/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */
package com.mytiki.tiki_sdk_android.core.req

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ReqTitleGet(
    val id: String?,
    val origin: String?
) {
    fun toJson(): String {
        val builder = StringBuilder()
        builder.append("{")
        builder.append("\"id\":").append("\"").append(id).append("\"").append(",")
        builder.append("\"origin\":").append("\"").append(origin).append("\"")
        builder.append("}")
        return builder.toString()
    }
}