/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */
package com.mytiki.tiki_sdk_android.tiki_platform_channel.req

import com.mytiki.tiki_sdk_android.TikiSdkDestination
import com.squareup.moshi.JsonClass

/**
 * The request for the `modifyConsent`method call in the Platform Channel.
 *
 * @property requestId
 * @property ownershipId
 * @property destination
 * @property about
 * @property reward
 * @property expiry
 * @constructor Create empty Req consent modify
 */
@JsonClass(generateAdapter = true)
data class ReqConsentModify(
    override val requestId: String,
    val ownershipId: String,
    val destination: TikiSdkDestination,
    val about: String?,
    val reward: String?,
    val expiry: Int?
) : Req(requestId)
