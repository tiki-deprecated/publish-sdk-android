/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */
package com.mytiki.tiki_sdk_android.trail

import com.mytiki.tiki_sdk_android.trail.rsp.RspLicense
import java.util.Date

data class LicenseRecord(
    val id: String,
    val title: TitleRecord,
    val uses: List<Use>,
    val terms: String,
    val description: String?,
    val expiry: Date?
) {
    companion object {
        fun from(rsp: RspLicense): LicenseRecord {
            return LicenseRecord(
                rsp.id!!,
                TitleRecord.from(rsp.title!!),
                rsp.uses ?: emptyList(),
                rsp.terms!!,
                rsp.description,
                rsp.expiry
            )
        }
    }
}
