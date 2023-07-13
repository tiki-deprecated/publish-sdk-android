/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.tiki_sdk_android.trail

import com.mytiki.tiki_sdk_android.channel.Channel
import com.mytiki.tiki_sdk_android.trail.req.ReqReceipt
import com.mytiki.tiki_sdk_android.trail.req.ReqReceiptAll
import com.mytiki.tiki_sdk_android.trail.req.ReqReceiptGet
import com.mytiki.tiki_sdk_android.trail.rsp.RspReceipt
import com.mytiki.tiki_sdk_android.trail.rsp.RspReceipts
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.async

class Receipt(private val channel: Channel) {
    
    fun create(
        payableId: String,
        amount: String,
        description: String? = null,
        reference: String? = null
    ): Deferred<ReceiptRecord> {
        return MainScope().async {
            val rsp: RspReceipt = channel.request(
                TrailMethod.RECEIPT_CREATE,
                ReqReceipt(payableId, amount, description, reference)
            ) { args ->
                RspReceipt.from(args)
            }.await()
            ReceiptRecord.from(rsp)
        }
    }

    fun get(id: String): Deferred<ReceiptRecord> {
        return MainScope().async {
            val rsp: RspReceipt = channel.request(
                TrailMethod.RECEIPT_GET,
                ReqReceiptGet(id)
            ) { args ->
                RspReceipt.from(args)
            }.await()
            ReceiptRecord.from(rsp)
        }
    }

    fun all(payableId: String): Deferred<List<ReceiptRecord>> {
        return MainScope().async {
            val rsp: RspReceipts = channel.request(
                TrailMethod.RECEIPT_ALL,
                ReqReceiptAll(payableId)
            ) { args ->
                RspReceipts.from(args)
            }.await()
            rsp.receipts?.map { receipt -> ReceiptRecord.from(receipt) } ?: emptyList()
        }
    }
}