/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */
package com.mytiki.tiki_sdk_android

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

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
@JsonClass(generateAdapter = true)
data class LicenseUse(
    /**
     * Usecases explicitly define HOW an asset may be used.
     */
    @Json(name = "usecases") val usecases: List<LicenseUsecase>,

    /**
     * Destinations explicitly define WHERE an asset may be used.
     * Destinations can be: a wildcard URL (*.your-co.com),
     * a string defining a category of
     */
    @Json(name = "destinations") val destinations: List<String>? = null
)
