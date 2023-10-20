/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.tiki_sdk_android.trail.req

import com.mytiki.tiki_sdk_android.channel.req.Req
import com.mytiki.tiki_sdk_android.trail.Usecase

data class ReqGuard(
    val ptr: String,
    val usecases: List<Usecase>,
    val destinations: List<String>? = null,
    val origin: String? = null
) : Req() {
    override fun map(): Map<String, Any?> {
        return mapOf(
            "requestId" to requestId,
            "ptr" to ptr,
            "usecases" to usecases.map { usecase -> usecase.value },
            "destinations" to destinations,
            "origin" to origin
        )
    }
}
