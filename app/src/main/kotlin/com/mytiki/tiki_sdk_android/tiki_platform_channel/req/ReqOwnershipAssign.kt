/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.tiki_sdk_android.tiki_platform_channel.req

import com.mytiki.tiki_sdk_android.TikiSdkDataTypeEnum
import com.squareup.moshi.JsonClass

/**
 * The request for the `assignOwnership` method call in the Platform Channel.
 *

 * @property source
 * @property type
 * @property contains
 * @property about
 * @property origin
 * @constructor Create empty Req ownership assign
 */
@JsonClass(generateAdapter = true)
data class ReqOwnershipAssign(
    val source: String,
    val type: TikiSdkDataTypeEnum,
    val contains: List<String>,
    val about: String? = null,
    val origin: String? = null
)