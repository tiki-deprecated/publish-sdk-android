/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */
package com.mytiki.tiki_sdk_android.trail

import com.mytiki.tiki_sdk_android.trail.rsp.RspPayable
import java.util.Date

data class PayableRecord(
    val id: String,
    val license: LicenseRecord,
    val amount: String,
    val type: String?,
    val description: String?,
    val expiry: Date?,
    val reference: String?,
) {
    companion object {
        fun from(rsp: RspPayable): PayableRecord {
            return PayableRecord(
                rsp.id!!,
                LicenseRecord.from(rsp.license!!),
                rsp.amount!!,
                rsp.type,
                rsp.description,
                rsp.expiry,
                rsp.reference
            )
        }
    }
}
