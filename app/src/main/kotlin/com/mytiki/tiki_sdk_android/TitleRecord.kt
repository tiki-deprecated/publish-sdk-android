/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */
package com.mytiki.tiki_sdk_android

import org.json.JSONObject

/**
 * Title record
 *
 * Title Records describe a data asset and MUST contain a Pointer Record to
 * your system. [Learn more](https://docs.mytiki.com/docs/offer-customization)
 * about Title Records.
 *
 * @property id This record's id.
 * @property ptr A Pointer Record identifying the asset.
 * @property tags A list of search-friendly tags describing the asset.
 * @property description A human-readable description of the asset.
 * @property origin Overrides the default origin from which the data was generated.
 * @constructor Create new Title record
 */
data class TitleRecord(
    /**
     * This record's id.
     */
    val id: String,
    /**
     * A Pointer Record identifying the asset
     */
    val ptr: String,
    /**
     * A list of search-friendly tags describing the asset.
     */
    val tags: List<TitleTag> = emptyList(),
    /**
     * A human-readable description of the asset.
     */
    val description: String? = null,
    /**
     * The origin from which the data was generated.
     */
    val origin: String? = null,
) {
    companion object {

        /**
         * Builds a Title Record from JSON String
         *
         * @param json String
         * @return [TitleRecord]
         */
        fun fromJson(json: String): TitleRecord? {
            if (json == "null") {
                return null
            }
            val jsonObj = JSONObject(json)
            val id = jsonObj.getString("id")
            val ptr = jsonObj.getString("ptr")
            val description = jsonObj.getString("description")
            val origin = jsonObj.getString("origin")
            val tagsArr = jsonObj.getJSONArray("tags")
            val tags = mutableListOf<TitleTag>()
            for (i in 0 until tagsArr.length()) {
                tags.add(TitleTag.fromJson(tagsArr[i] as String))
            }
            return TitleRecord(id, ptr, tags, description, origin)
        }
    }
}
