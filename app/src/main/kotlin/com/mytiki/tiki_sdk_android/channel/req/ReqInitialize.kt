/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.tiki_sdk_android.channel.req

data class ReqInitialize(
    val id: String,
    val publishingId: String,
    val origin: String,
    val dir: String
) : Req() {
    override fun map(): Map<String, Any> {
        return mapOf(
            "requestId" to requestId,
            "publishingId" to publishingId,
            "origin" to origin,
            "id" to id,
            "dir" to dir
        )
    }
}