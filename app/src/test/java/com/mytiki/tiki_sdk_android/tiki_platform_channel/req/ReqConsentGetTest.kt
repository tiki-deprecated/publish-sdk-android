package com.mytiki.tiki_sdk_android.tiki_platform_channel.req

import com.mytiki.tiki_sdk_android.tiki_platform_channel.req.ReqConsentGet
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import org.junit.Assert
import org.junit.Test

class ReqConsentGetTest {
    @Test
    fun encode_ReqConsentGet_from_JSON() {
        val moshi: Moshi = Moshi.Builder().build()
        val adapter: JsonAdapter<ReqConsentGet> = moshi.adapter(ReqConsentGet::class.java)
        val json = "{\"requestId\":\"reqId\",\"source\":\"source\",\"origin\":\"origin\"}"
        val req = adapter.fromJson(json)
        Assert.assertEquals(req!!.requestId, "reqId")
        Assert.assertEquals(req.source, "source")
        Assert.assertEquals(req.origin, "origin")
    }

    @Test
    fun encode_ReqConsentGet_to_JSON() {
        val moshi: Moshi = Moshi.Builder().build()
        val adapter: JsonAdapter<ReqConsentGet> = moshi.adapter(ReqConsentGet::class.java)
        val req = ReqConsentGet("reqId", "source", "origin")
        val json = adapter.toJson(req)
        Assert.assertEquals("{\"requestId\":\"reqId\",\"source\":\"source\",\"origin\":\"origin\"}",json)
    }
}