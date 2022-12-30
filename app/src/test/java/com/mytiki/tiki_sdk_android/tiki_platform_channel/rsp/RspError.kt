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
        val json = "{\"message\":\"error\",\"stackTrace\":\"stackTrace\"}"
        val req = adapter.fromJson(json)
        Assert.assertEquals(req!!.message, "error")
    }

    @Test
    fun encode_RspError_to_JSON() {
        val moshi: Moshi = Moshi.Builder().build()
        val adapter: JsonAdapter<RspError> = moshi.adapter(RspError::class.java)
        val rsp = RspError("error", "stackTrace")
        val json = adapter.toJson(rsp)
        Assert.assertEquals(
            "{\"message\":\"error\",\"stackTrace\":\"stackTrace\"}",
            json
        )
    }
}