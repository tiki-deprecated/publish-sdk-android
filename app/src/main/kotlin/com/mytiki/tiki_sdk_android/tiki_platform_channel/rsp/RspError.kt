/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.tiki_sdk_android.tiki_platform_channel.rsp

import com.squareup.moshi.JsonClass

/**
 * The response for the errors thrown in the Platform Channel.
 *
 * It returns the *message* of the error and the String representation of the [stackTrace].
 *

 * @property message
 * @property stackTrace
 * @constructor Create empty Rsp error
 */
@JsonClass(generateAdapter = true)
data class RspError(
    val message: String?,
    val stackTrace: String?
)
