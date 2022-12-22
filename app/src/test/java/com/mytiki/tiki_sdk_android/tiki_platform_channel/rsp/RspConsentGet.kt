/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */
package com.mytiki.tiki_sdk_android.tiki_platform_channel.rsp

import com.mytiki.tiki_sdk_android.TikiSdkConsent
import com.mytiki.tiki_sdk_android.TikiSdkDestination
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import org.junit.Assert
import org.junit.Test

class RspConsentGetTest {
    @Test
    fun encode_RspConsentGet_from_JSON() {
        val moshi: Moshi = Moshi.Builder().build()
        val adapter: JsonAdapter<RspConsentGet> = moshi.adapter(RspConsentGet::class.java)
        val json =
            "{\"requestId\":\"reqId\",\"consent\":{\"ownershipId\":\"ownershipId\",\"destination\":{\"paths\":[\"*\"],\"uses\":[\"*\"]},\"transactionId\":\"txnId\"}}"
        val req = adapter.fromJson(json)
        Assert.assertEquals("reqId", req!!.requestId)
    }

    @Test
    fun encode_RspConsentGet_to_JSON() {
        val moshi: Moshi = Moshi.Builder().build()
        val adapter: JsonAdapter<RspConsentGet> = moshi.adapter(RspConsentGet::class.java)
        val rsp =
            RspConsentGet("reqId", TikiSdkConsent("ownershipId", TikiSdkDestination.ALL, "txnId"))
        val json = adapter.toJson(rsp)
        Assert.assertEquals(
            "{\"requestId\":\"reqId\",\"consent\":{\"ownershipId\":\"ownershipId\",\"destination\":{\"paths\":[\"*\"],\"uses\":[\"*\"]},\"transactionId\":\"txnId\"}}",
            json
        )
    }
}