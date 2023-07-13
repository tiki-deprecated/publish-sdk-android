/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */
package com.mytiki.tiki_sdk_android.trail

import com.mytiki.tiki_sdk_android.trail.rsp.RspReceipt

data class ReceiptRecord(
    val id: String,
    val payable: PayableRecord,
    val amount: String,
    val description: String?,
    val reference: String?,
) {
    companion object {
        fun from(rsp: RspReceipt): ReceiptRecord {
            return ReceiptRecord(
                rsp.id!!,
                PayableRecord.from(rsp.payable!!),
                rsp.amount!!,
                rsp.description,
                rsp.reference
            )
        }
    }
}
