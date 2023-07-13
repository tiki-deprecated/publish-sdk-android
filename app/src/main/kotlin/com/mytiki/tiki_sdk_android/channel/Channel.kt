/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.tiki_sdk_android.channel

import android.content.Context
import com.mytiki.tiki_sdk_android.channel.req.Req
import com.mytiki.tiki_sdk_android.channel.req.ReqInitialize
import com.mytiki.tiki_sdk_android.channel.rsp.Rsp
import com.mytiki.tiki_sdk_android.trail.rsp.RspInitialize
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred

class Channel {
    private var handler: ChannelHandler? = null

    fun initialize(id: String, publishingId: String, context: Context): Deferred<RspInitialize> {
        handler = ChannelHandler(context)
        return request(
            DefaultMethod.INITIALIZE,
            ReqInitialize(id, publishingId, context.packageName, context.applicationInfo.dataDir)
        ) { args -> RspInitialize.from(args) }
    }

    fun <S : Req, D : Rsp> request(
        method: ChannelMethod,
        request: S,
        toResponse: (Map<String, Any?>) -> D
    ): CompletableDeferred<D> {
        if (handler == null)
            throw IllegalStateException("Channel not initialized. Call .initialize(...)")
        return handler!!.invokeMethod(
            method,
            request
        ) { call -> toResponse(call.arguments as Map<String, Any?>) }
    }
}