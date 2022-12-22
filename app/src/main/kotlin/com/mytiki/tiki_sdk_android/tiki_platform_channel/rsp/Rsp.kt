/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */
package com.mytiki.tiki_sdk_android.tiki_platform_channel.rsp

import com.squareup.moshi.JsonClass

/**
 * The base Response object for native channels.
 *
 * The [requestId] parameter is used to identify the request in the native platform.
 *
 * @property requestId
 * @constructor Create empty Rsp
 */
@JsonClass(generateAdapter = true)
open class Rsp(
    open val requestId: String
)
