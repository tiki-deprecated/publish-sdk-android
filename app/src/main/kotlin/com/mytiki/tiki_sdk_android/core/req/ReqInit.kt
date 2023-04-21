/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */
package com.mytiki.tiki_sdk_android.core.req

import org.json.JSONObject

data class ReqInit(
    val publishingId: String,
    val id: String,
    val dbDir: String,
    val origin: String,
) {
    fun toJson(): String {
        val jsonObject = JSONObject()
        jsonObject.put("publishingId", publishingId)
        jsonObject.put("id", id)
        jsonObject.put("origin", origin)
        jsonObject.put("dbDir", dbDir)
        return jsonObject.toString()
    }
}
