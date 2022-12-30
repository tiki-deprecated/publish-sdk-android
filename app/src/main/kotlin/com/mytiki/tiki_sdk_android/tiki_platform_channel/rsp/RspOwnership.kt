/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.tiki_sdk_android.tiki_platform_channel.rsp

import com.mytiki.tiki_sdk_android.TikiSdkOwnership
import com.squareup.moshi.JsonClass

/**
 * The response for the `assignOwnership` and `getOwnership` method calls in the Platform Channel.
 *
 * Returns the [ownership] or Null if not found.
 *

 * @property ownership
 * @constructor Create empty Rsp ownership
 */
@JsonClass(generateAdapter = true)
data class RspOwnership(
    val ownership: TikiSdkOwnership
)
