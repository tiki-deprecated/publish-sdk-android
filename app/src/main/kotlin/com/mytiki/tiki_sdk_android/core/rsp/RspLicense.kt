/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */
package com.mytiki.tiki_sdk_android.core.rsp

import com.mytiki.tiki_sdk_android.LicenseRecord
import org.json.JSONObject

data class RspLicense(
    val license: LicenseRecord?
) {
    companion object {
        fun fromJson(json: String): RspLicense {
            val jsonObj = JSONObject(json)
            return RspLicense(LicenseRecord.fromJson(jsonObj.optString("license")))
        }
    }
}