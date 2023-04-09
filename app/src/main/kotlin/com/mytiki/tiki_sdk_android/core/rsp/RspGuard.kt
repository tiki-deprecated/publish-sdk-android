/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */
package com.mytiki.tiki_sdk_android.core.rsp

import org.json.JSONObject

data class RspGuard(
    val success: Boolean,
    val reason: String?
) {
    companion object {
        fun fromJson(json: String): RspGuard {
            val jsonObj = JSONObject(json)
            return RspGuard(jsonObj.getBoolean("success"), jsonObj.optString("reason"))
        }
    }
}
