/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */
package com.mytiki.tiki_sdk_android.tiki_platform_channel.req

import com.mytiki.tiki_sdk_android.TikiSdkDestination
import com.squareup.moshi.JsonClass

/**
 * The request for the `applyConsent` method call in the Platform Channel.
 *
 * It uses the [source] and [destination] to verify if the consent was given.
 *
 * @property requestId
 * @property source
 * @property destination
 * @property origin
 * @constructor Create empty Req consent apply
 */
@JsonClass(generateAdapter = true)
data class ReqConsentApply(
    override val requestId: String,
    val source: String,
    val destination: TikiSdkDestination,
    val origin: String? = null
) : Req(requestId)
