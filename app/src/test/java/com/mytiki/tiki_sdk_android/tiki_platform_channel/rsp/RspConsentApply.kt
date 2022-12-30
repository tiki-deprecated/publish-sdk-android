/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */
package com.mytiki.tiki_sdk_android.tiki_platform_channel.rsp

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import org.junit.Assert
import org.junit.Test

class RspConsentApplyTest {
    @Test
    fun encode_RspConsentApply_from_JSON() {
        val moshi: Moshi = Moshi.Builder().build()
        val adapter: JsonAdapter<RspConsentApply> = moshi.adapter(RspConsentApply::class.java)
        val json = "{\"success\":true}"
        val rsp = adapter.fromJson(json)
        Assert.assertTrue(rsp!!.success)
    }

    @Test
    fun encode_RspConsentApply_to_JSON() {
        val moshi: Moshi = Moshi.Builder().build()
        val adapter: JsonAdapter<RspConsentApply> = moshi.adapter(RspConsentApply::class.java)
        val rsp = RspConsentApply(true)
        val json = adapter.toJson(rsp)
        Assert.assertEquals("{\"success\":true}", json)
    }
}