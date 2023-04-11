/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */
package com.mytiki.tiki_sdk_android.core.req

import com.mytiki.tiki_sdk_android.LicenseUsecase
import org.json.JSONArray
import org.json.JSONObject

data class ReqGuard(
    var ptr: String? = null,
    var usecases: List<LicenseUsecase> = emptyList(),
    var destinations: List<String>? = null,
    var origin: String? = null
) {
    fun toJson(): String {
        val jsonObject = JSONObject()
        jsonObject.put("ptr", ptr)
        val usesArray = JSONArray()
        for (i in usecases.indices) {
            usesArray.put(usecases[i].value)
        }
        jsonObject.put("usecases", usesArray)
        val destinationsArray = JSONArray()
        if (destinations != null) {
            for (i in destinations!!.indices) {
                usesArray.put(destinations!![i])
            }
        }
        jsonObject.put("destinations", destinationsArray)
        jsonObject.put("origin", origin)
        return jsonObject.toString()
    }
}
