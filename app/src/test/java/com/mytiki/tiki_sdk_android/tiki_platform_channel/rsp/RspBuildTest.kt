/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */
package com.mytiki.tiki_sdk_android.tiki_platform_channel.rsp


import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import org.junit.Assert
import org.junit.Test

class RspBuildTest {
    @Test
    fun encode_RspBuild_from_JSON() {
        val moshi: Moshi = Moshi.Builder().build()
        val adapter: JsonAdapter<RspBuild> = moshi.adapter(RspBuild::class.java)
        val json = "{\"address\":\"address\"}"
        val req = adapter.fromJson(json)
        Assert.assertEquals("address", req!!.address)
    }

    @Test
    fun encode_RspBuild_to_JSON() {
        val moshi: Moshi = Moshi.Builder().build()
        val adapter: JsonAdapter<RspBuild> = moshi.adapter(RspBuild::class.java)
        val req = RspBuild("address")
        val json = adapter.toJson(req)
        Assert.assertEquals("{\"address\":\"address\"}", json)
    }
}