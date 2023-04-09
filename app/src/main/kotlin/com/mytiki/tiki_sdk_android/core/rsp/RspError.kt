/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */
package com.mytiki.tiki_sdk_android.core.rsp

import org.json.JSONObject

data class RspError(
    val message: String,
    val stackTrace: String
) {
    companion object {
        fun fromJson(json: String): RspError {
            val jsonObj = JSONObject(json)
            return RspError(jsonObj.getString("message"), jsonObj.getString("stackTrace"))
        }
    }
}
