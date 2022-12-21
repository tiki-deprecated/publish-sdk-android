package com.mytiki.tiki_sdk_android

import com.mytiki.tiki_sdk_android.tiki_platform_channel.req.ReqConsentModify
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import org.junit.Assert
import org.junit.Test

class ReqConsentModifyTest {
    @Test
    fun encode_ReqConsentModify_from_JSON() {
        val moshi: Moshi = Moshi.Builder().build()
        val adapter: JsonAdapter<ReqConsentModify> = moshi.adapter(ReqConsentModify::class.java)
        val json = "{\"requestId\":\"reqId\",\"ownershipId\":\"ownershipId\",\"destination\":{\"paths\":[\"*\"],\"uses\":[\"*\"]}}"
        val req = adapter.fromJson(json)
        Assert.assertEquals( "*", req!!.destination.paths[0])
        Assert.assertEquals( "ownershipId", req.ownershipId)
        Assert.assertEquals( "reqId", req.requestId)
    }

    @Test
    fun encode_ReqConsentModify_to_JSON() {
        val moshi: Moshi = Moshi.Builder().build()
        val adapter: JsonAdapter<ReqConsentModify> = moshi.adapter(ReqConsentModify::class.java)
        val req = ReqConsentModify("reqId", "ownershipId", TikiSdkDestination.ALL)
        val json = adapter.toJson(req)
        Assert.assertEquals("{\"requestId\":\"reqId\",\"ownershipId\":\"ownershipId\",\"destination\":{\"paths\":[\"*\"],\"uses\":[\"*\"]}}",json)
    }
}