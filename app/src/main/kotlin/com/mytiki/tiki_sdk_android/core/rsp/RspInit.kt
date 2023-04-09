/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */
package com.mytiki.tiki_sdk_android.core.rsp

import org.json.JSONObject

data class RspInit(
    val address: String
) {
    companion object {
        fun fromJson(json: String): RspInit {
            val jsonObj = JSONObject(json)
            return RspInit(jsonObj.getString("address"))
        }
    }
}