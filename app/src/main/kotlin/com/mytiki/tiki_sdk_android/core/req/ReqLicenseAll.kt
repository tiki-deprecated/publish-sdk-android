/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */
package com.mytiki.tiki_sdk_android.core.req

import org.json.JSONObject

data class ReqLicenseAll(
    val ptr: String?,
    val origin: String?
) {
    fun toJson(): String {
        val jsonObject = JSONObject()
        jsonObject.put("ptr", ptr)
        jsonObject.put("origin", origin)
        return jsonObject.toString()
    }
}
