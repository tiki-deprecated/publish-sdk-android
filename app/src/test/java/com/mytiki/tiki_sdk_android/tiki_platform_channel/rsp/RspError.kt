/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */
package com.mytiki.tiki_sdk_android.tiki_platform_channel.rsp

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import org.junit.Assert
import org.junit.Test

class RspErrorTest {
    @Test
    fun encode_RspError_from_JSON() {
        val moshi: Moshi = Moshi.Builder().build()
        val adapter: JsonAdapter<RspError> = moshi.adapter(RspError::class.java)
        val json = "{\"requestId\":\"reqId\",\"message\":\"error\",\"stackTrace\":\"stackTrace\"}"
        val req = adapter.fromJson(json)
        Assert.assertEquals("reqId", req!!.requestId)
    }

    @Test
    fun encode_RspError_to_JSON() {
        val moshi: Moshi = Moshi.Builder().build()
        val adapter: JsonAdapter<RspError> = moshi.adapter(RspError::class.java)
        val rsp = RspError("reqId", "error", "stackTrace")
        val json = adapter.toJson(rsp)
        Assert.assertEquals(
            "{\"requestId\":\"reqId\",\"message\":\"error\",\"stackTrace\":\"stackTrace\"}",
            json
        )
    }
}