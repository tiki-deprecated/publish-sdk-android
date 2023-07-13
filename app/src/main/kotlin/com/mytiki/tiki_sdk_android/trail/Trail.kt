/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.tiki_sdk_android.trail

import com.mytiki.tiki_sdk_android.channel.Channel
import com.mytiki.tiki_sdk_android.channel.req.ReqDefault
import com.mytiki.tiki_sdk_android.trail.req.ReqGuard
import com.mytiki.tiki_sdk_android.trail.rsp.RspAddress
import com.mytiki.tiki_sdk_android.trail.rsp.RspGuard
import com.mytiki.tiki_sdk_android.trail.rsp.RspId
import com.mytiki.tiki_sdk_android.trail.rsp.RspIsInitialized
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.async

class Trail(private val channel: Channel) {
    val title: Title = Title(channel)
    val license: License = License(channel)
    val payable: Payable = Payable(channel)
    val receipt: Receipt = Receipt(channel)

    fun isInitialized(): Deferred<Boolean> {
        return MainScope().async {
            channel.request(
                TrailMethod.IS_INITIALIZED,
                ReqDefault()
            ) { args ->
                RspIsInitialized.from(args)
            }.await().isInitialized
        }
    }

    fun address(): Deferred<String?> {
        return MainScope().async {
            channel.request(
                TrailMethod.ADDRESS,
                ReqDefault()
            ) { args ->
                RspAddress.from(args)
            }.await().address
        }
    }

    fun id(): Deferred<String?> {
        return MainScope().async {
            channel.request(
                TrailMethod.ID,
                ReqDefault()
            ) { args ->
                RspId.from(args)
            }.await().id
        }
    }

    fun guard(
        ptr: String,
        usecases: List<Usecase>,
        destinations: List<String>? = null,
        onPass: (() -> Unit)? = null,
        onFail: ((String?) -> Unit)? = null,
        origin: String? = null
    ): Deferred<Boolean> {
        return MainScope().async {
            val rsp: RspGuard = channel.request(
                TrailMethod.GUARD,
                ReqGuard(ptr, usecases, destinations, origin)
            ) { args ->
                RspGuard.from(args)
            }.await()
            if (onPass != null && rsp.success) onPass()
            else if (onFail != null && !rsp.success) onFail(rsp.reason)
            rsp.success
        }
    }
}