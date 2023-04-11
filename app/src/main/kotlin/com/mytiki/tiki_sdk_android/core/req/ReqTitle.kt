/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */
package com.mytiki.tiki_sdk_android.core.req

import com.mytiki.tiki_sdk_android.TitleTag
import org.json.JSONArray
import org.json.JSONObject

data class ReqTitle(
    val ptr: String?,
    val tags: List<TitleTag> = emptyList(),
    val description: String?,
    val origin: String?
) {
    fun toJson(): String {
        val jsonObject = JSONObject()
        jsonObject.put("ptr", ptr)
        val tagsArray = JSONArray()
        for (i in tags.indices) {
            tagsArray.put(tags[i].value)
        }
        jsonObject.put("tags", tagsArray)
        jsonObject.put("description", description)
        jsonObject.put("origin", origin)
        return jsonObject.toString()
    }
}