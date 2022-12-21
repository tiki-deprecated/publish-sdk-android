/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */
package com.mytiki.tiki_sdk_android.tiki_platform_channel.rsp

import com.mytiki.tiki_sdk_android.TikiSdkConsent
import com.squareup.moshi.JsonClass

/**
 * The response for the `getConsent` and `modifyConsent`method calls in the Platform Channel.
 *
 * It returns the [consent]. Null if no consent was given.
 *
 * @property requestId
 * @property consent
 * @constructor Create empty Rsp consent get
 */
@JsonClass(generateAdapter = true)
data class RspConsentGet(
    override val requestId: String,
    val consent: TikiSdkConsent
): Rsp(requestId)
