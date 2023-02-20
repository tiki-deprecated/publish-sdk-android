/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.tiki_sdk_android

import com.squareup.moshi.JsonClass

/**
 * The destination to which the data is consented to be used.
 *
 * @property paths
 * @property uses defaults to all uses
 * @constructor Create empty Tiki sdk destination
 */
@JsonClass(generateAdapter = true)
open class TikiSdkDestination(
    /**
     * The destination paths.
     *
     * A list of paths, preferably URL without the scheme or reverse-DNS.
     * Keep list short and use wildcard () matching. Prefix with NOT to invert.
     */
    val paths: List<String>,

    /**
     * The destination use cases
     *
     * List of optional application-specific use cases applicable to the given
     * destination.
     * Use the prefix "NOT" to invert a use case. For example, "NOT ads" means
     * that the data should not be used for ads.
     */
    val uses: List<String> = listOf("*"),
) {
    /**
     * TikiSdkDestination.ALL
     *
     * @constructor Create a TikiSdkDestination that includes all paths and uses.
     */
    object ALL : TikiSdkDestination(listOf("*"))

    /**
     * TikiSdkDestination.NONE
     *
     * Should be used for denying all consents given before.
     *
     * @constructor Create a TikiSdkDestination without any paths or uses.
     */
    object NONE : TikiSdkDestination(listOf(), listOf())
}
