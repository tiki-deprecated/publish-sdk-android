/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.tiki_sdk_android.channel.req

import java.util.UUID

abstract class Req {
    val requestId: String = UUID.randomUUID().toString()
    abstract fun map(): Map<String, Any?>
}