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
     * A list of paths, preferably URL without the scheme or
     * reverse FQDN. Keep list short and use wildcard (*)
     * matching. Prefix with NOT to invert.
     * _i.e. NOT mytiki.com
     */
    val paths: List<String>,

    /**
     * The destination use cases.
     *
     * An optional list of application specific uses cases
     * applicable to the given destination. Prefix with NOT
     * to invert. _i.e. NOT ads
     */
    val uses: List<String> = listOf("*"),
){
    /**
     * TikiSdkDestination.ALL
     *
     * @constructor Create a TikiSdkDestination that includes all paths and uses.
     */
    object ALL : TikiSdkDestination(listOf("*"))

    ///
    ///
    ///
    /**
     * TikiSdkDestination.NONE
     *
     * Should be used for denying all consents given before.
     *
     * @constructor Create a TikiSdkDestination without any paths or uses.
     */
    object NONE : TikiSdkDestination(listOf(), listOf())
}
