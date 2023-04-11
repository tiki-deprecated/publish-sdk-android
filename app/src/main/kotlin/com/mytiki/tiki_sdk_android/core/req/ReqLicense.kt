/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */
package com.mytiki.tiki_sdk_android.core.req

import com.mytiki.tiki_sdk_android.LicenseUse
import com.mytiki.tiki_sdk_android.TitleTag
import org.json.JSONArray
import org.json.JSONObject
import java.util.*

data class ReqLicense(
    val ptr: String?,
    val terms: String?,
    val titleDescription: String?,
    val licenseDescription: String?,
    val uses: List<LicenseUse> = emptyList(),
    val tags: List<TitleTag> = emptyList(),
    val expiry: Date?,
    val origin: String?
) {
    fun toJson(): String {
        val jsonObject = JSONObject()
        jsonObject.put("ptr", ptr)
        jsonObject.put("terms", terms)
        jsonObject.put("titleDescription", titleDescription)
        jsonObject.put("licenseDescription", licenseDescription)
        val usesArray = JSONArray()
        for (i in uses.indices) {
            usesArray.put(uses[i].toJsonObject())
        }
        jsonObject.put("uses", usesArray)
        val tagsArray = JSONArray()
        for (i in tags.indices) {
            tagsArray.put(tags[i].value)
        }
        jsonObject.put("tags", tagsArray)
        jsonObject.put("expiry", expiry?.time)
        jsonObject.put("origin", origin)
        return jsonObject.toString()
    }
}