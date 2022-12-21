/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */
package com.mytiki.tiki_sdk_android.tiki_platform_channel.rsp

import com.squareup.moshi.JsonClass


/**
 * The response for the `build` method call in the Platform Channel.
 *
 * @property requestId
 * @property address The [address] of the built node.
 * @constructor Create empty Rsp build
 */
@JsonClass(generateAdapter = true)
data class RspBuild (
    override val requestId: String,
    val address : String
): Rsp(requestId)
