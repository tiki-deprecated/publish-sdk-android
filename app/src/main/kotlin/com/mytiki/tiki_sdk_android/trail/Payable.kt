/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.tiki_sdk_android.trail

import com.mytiki.tiki_sdk_android.channel.Channel
import com.mytiki.tiki_sdk_android.trail.req.ReqPayable
import com.mytiki.tiki_sdk_android.trail.req.ReqPayableAll
import com.mytiki.tiki_sdk_android.trail.req.ReqPayableGet
import com.mytiki.tiki_sdk_android.trail.rsp.RspPayable
import com.mytiki.tiki_sdk_android.trail.rsp.RspPayables
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.async
import java.util.Date

class Payable(private val channel: Channel) {
    
    fun create(
        licenseId: String,
        amount: String,
        type: String,
        expiry: Date? = null,
        description: String? = null,
        reference: String? = null
    ): Deferred<PayableRecord> {
        return MainScope().async {
            val rsp: RspPayable = channel.request(
                TrailMethod.PAYABLE_CREATE,
                ReqPayable(licenseId, amount, type, expiry, description, reference)
            ) { args ->
                RspPayable.from(args)
            }.await()
            PayableRecord.from(rsp)
        }
    }

    fun get(id: String): Deferred<PayableRecord> {
        return MainScope().async {
            val rsp: RspPayable = channel.request(
                TrailMethod.PAYABLE_GET,
                ReqPayableGet(id)
            ) { args ->
                RspPayable.from(args)
            }.await()
            PayableRecord.from(rsp)
        }
    }

    fun all(licenseId: String): Deferred<List<PayableRecord>> {
        return MainScope().async {
            val rsp: RspPayables = channel.request(
                TrailMethod.PAYABLE_ALL,
                ReqPayableAll(licenseId)
            ) { args ->
                RspPayables.from(args)
            }.await()
            rsp.payables?.map { payable -> PayableRecord.from(payable) } ?: emptyList()
        }
    }
}