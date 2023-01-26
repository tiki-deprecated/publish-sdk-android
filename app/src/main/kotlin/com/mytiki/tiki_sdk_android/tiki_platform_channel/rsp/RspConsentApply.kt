/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.tiki_sdk_android.tiki_platform_channel.rsp

import com.squareup.moshi.JsonClass

/**
 * The response for the `applyConsent` method call in the Platform Channel.
 *
 * It returns if the consent was applied in the *success* field.
 * For failed requests, a *reason* should be provided.
 *

 * @property success
 * @property reason
 * @constructor Create empty Rsp consent apply
 */
@JsonClass(generateAdapter = true)
data class RspConsentApply(
    val success: Boolean,
    val reason: String? = null
)
