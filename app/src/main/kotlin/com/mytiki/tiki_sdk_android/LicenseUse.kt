/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */
package com.mytiki.tiki_sdk_android

import org.json.JSONArray
import org.json.JSONObject

/**
 * License use
 *
 * Define explicit uses for an asset. [LicenseUse]s are extremely helpful in programmatic search
 * and enforcement of your [LicenseRecord]s.
 *
 * [usecases] explicitly define HOW an asset may be used. Use either our list of common enumerations
 * or define your own using [LicenseUsecase]
 *
 * [destinations] define WHO can use an asset. [destinations] narrow down [usecases] to a set of
 * URLs, categories of companies, or more. Use ECMAScript Regex to specify flexible and easily
 * enforceable rules.
 *
 * @property usecases
 * @property destinations
 * @constructor Create empty License use
 */

data class LicenseUse(
    /**
     * Usecases explicitly define HOW an asset may be used.
     */
    val usecases: List<LicenseUsecase>,

    /**
     * Destinations explicitly define WHERE an asset may be used.
     * Destinations can be: a wildcard URL (*.your-co.com),
     * a string defining a category of
     */
    val destinations: List<String>? = null
) {
    companion object {
        fun fromJson(json: String): LicenseUse {
            val jsonObject = JSONObject(json)
            val usecasesArr = jsonObject.getJSONArray("usecases")
            val usecases = mutableListOf<LicenseUsecase>()
            for (i in 0 until usecasesArr.length()) {
                usecases.add(LicenseUsecase.fromJson(usecasesArr[i] as String))
            }
            val destinations = mutableListOf<String>()
            val destinationsArr = jsonObject.getJSONArray("destinations")
            for (i in 0 until destinationsArr.length()) {
                destinations.add(destinationsArr[i] as String)
            }
            return LicenseUse(usecases, destinations)
        }
    }

    fun toJsonObject(): JSONObject {
        val jsonObject = JSONObject()
        val usesArray = JSONArray()
        for (i in usecases.indices) {
            usesArray.put(usecases[i].value)
        }
        jsonObject.put("usecases", usesArray)
        val destinationsArray = JSONArray()
        if (destinations != null) {
            for (i in destinations.indices) {
                usesArray.put(destinations[i])
            }
        }
        jsonObject.put("destinations", destinationsArray)
        return jsonObject
    }

    fun toJson(): String {
        return toJsonObject().toString()
    }
}
