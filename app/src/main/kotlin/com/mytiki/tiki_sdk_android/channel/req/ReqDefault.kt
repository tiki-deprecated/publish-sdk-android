/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.tiki_sdk_android.channel.req

class ReqDefault : Req() {
    override fun map(): Map<String, Any> {
        return mapOf("requestId" to requestId)
    }
}