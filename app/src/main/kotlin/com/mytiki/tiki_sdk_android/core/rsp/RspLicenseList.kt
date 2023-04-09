/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */
package com.mytiki.tiki_sdk_android.core.rsp

import com.mytiki.tiki_sdk_android.LicenseRecord
import com.squareup.moshi.JsonClass
import org.json.JSONObject

@JsonClass(generateAdapter = true)
data class RspLicenseList(
    val licenseList: List<LicenseRecord>
) {
    companion object {
        fun fromJson(json: String): RspLicenseList {
            val licenses = mutableListOf<LicenseRecord>()
            val licenseArr = JSONObject(json).getJSONArray("licenseList")
            for (index in 0 until licenseArr.length()) {
                licenses.add(LicenseRecord.fromJson(licenseArr[index] as String)!!)
            }
            return RspLicenseList(licenses)
        }
    }
}