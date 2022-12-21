/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */
package com.mytiki.tiki_sdk_android.tiki_platform_channel.req

import com.squareup.moshi.JsonClass

/**
 * The base Request object for native channels.
 *
 * @property requestId used to identify the request in the native platform.
 * @constructor Creates a new generic request
 */
@JsonClass(generateAdapter = true)
open class Req(
    open val requestId: String
)
