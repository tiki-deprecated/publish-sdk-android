/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.tiki_sdk_android.trail

import com.mytiki.tiki_sdk_android.channel.Channel
import com.mytiki.tiki_sdk_android.trail.req.ReqLicense
import com.mytiki.tiki_sdk_android.trail.req.ReqLicenseAll
import com.mytiki.tiki_sdk_android.trail.req.ReqLicenseGet
import com.mytiki.tiki_sdk_android.trail.rsp.RspLicense
import com.mytiki.tiki_sdk_android.trail.rsp.RspLicenses
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.async
import java.util.Date

class License(private val channel: Channel) {
    
    fun create(
        titleId: String,
        uses: List<Use>,
        terms: String,
        expiry: Date? = null,
        description: String? = null
    ): Deferred<LicenseRecord> {
        return MainScope().async {
            val rsp: RspLicense = channel.request(
                TrailMethod.LICENSE_CREATE,
                ReqLicense(titleId, uses, terms, expiry, description)
            ) { args ->
                RspLicense.from(args)
            }.await()
            LicenseRecord.from(rsp)
        }
    }

    fun get(id: String): Deferred<LicenseRecord> {
        return MainScope().async {
            val rsp: RspLicense = channel.request(
                TrailMethod.LICENSE_GET,
                ReqLicenseGet(id)
            ) { args ->
                RspLicense.from(args)
            }.await()
            LicenseRecord.from(rsp)
        }
    }

    fun all(titleId: String): Deferred<List<LicenseRecord>> {
        return MainScope().async {
            val rsp: RspLicenses = channel.request(
                TrailMethod.LICENSE_ALL,
                ReqLicenseAll(titleId)
            ) { args ->
                RspLicenses.from(args)
            }.await()
            rsp.licenses?.map { license -> LicenseRecord.from(license) } ?: emptyList()
        }
    }
}