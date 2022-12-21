package com.mytiki.tiki_sdk_android

import com.mytiki.tiki_sdk_android.tiki_platform_channel.req.ReqBuild
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import org.junit.Assert
import org.junit.Test

class ReqBuildTest {
    @Test
    fun encode_ReqBuild_from_JSON() {
        val moshi: Moshi = Moshi.Builder().build()
        val adapter: JsonAdapter<ReqBuild> = moshi.adapter(ReqBuild::class.java)
        val json = "{\"requestId\":\"reqId\",\"apiId\":\"apiId\",\"origin\":\"origin\"}"
        val reqBuild = adapter.fromJson(json)
        Assert.assertEquals("reqId", reqBuild?.requestId)
        Assert.assertEquals("apiId", reqBuild?.apiId)
        Assert.assertEquals("origin", reqBuild?.origin)
        Assert.assertNull(reqBuild!!.address)

    }
    @Test
    fun encode_ReqBuild_to_JSON() {
        val moshi: Moshi = Moshi.Builder().build()
        val adapter: JsonAdapter<ReqBuild> = moshi.adapter(ReqBuild::class.java)
        val req = ReqBuild("reqId", "apiId", "origin")
        val json = adapter.toJson(req)
        Assert.assertEquals("{\"requestId\":\"reqId\",\"apiId\":\"apiId\",\"origin\":\"origin\"}", json)
    }
}