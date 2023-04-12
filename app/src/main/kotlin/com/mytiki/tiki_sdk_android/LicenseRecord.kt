/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */
package com.mytiki.tiki_sdk_android

import org.json.JSONObject
import java.util.*

/**
 * License Records describe the terms around which a data asset may be used
 * and MUST contain a reference to the corresponding Title Record.
 * [Learn more](https://docs.mytiki.com/docs/offer-customization)  about License Records.
 *
 * @property id This record's id
 * @property title The [TitleRecord] for this license
 * @property uses A list describing how an asset can be used
 * @property terms The legal terms for the license
 * @property description A human-readable description of the license
 * @property expiry The date when the license expires
 */
data class LicenseRecord(
    val id: String?,
    val title: TitleRecord,
    val uses: List<LicenseUse>,
    val terms: String,
    val description: String?,
    val expiry: Date?
) {
    companion object {

        /**
         * Builds a [LicenseRecord] from JSON String
         *
         * @param json
         * @return [LicenseRecord]
         */
        fun fromJson(json: String): LicenseRecord? {
            if (json == "null") {
                return null
            }
            val jsonObj = JSONObject(json)
            val id = jsonObj.getString("id")
            val terms = jsonObj.getString("terms")
            val description = jsonObj.getString("description")
            val title = TitleRecord.fromJson(jsonObj.getString("title"))!!
            val usesArray = jsonObj.getJSONArray("uses")
            val uses = mutableListOf<LicenseUse>()
            for (index in 0 until usesArray.length()) {
                uses.add(LicenseUse.fromJson(usesArray[index] as String))
            }
            val expiry = Date(jsonObj.getString("expiry").toLong())
            return LicenseRecord(id, title, uses, terms, description, expiry)
        }
    }
}
