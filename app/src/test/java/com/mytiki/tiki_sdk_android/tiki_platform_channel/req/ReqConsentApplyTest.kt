/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */
package com.mytiki.tiki_sdk_android.tiki_platform_channel.req

import com.mytiki.tiki_sdk_android.TikiSdkDestination
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import org.junit.Assert
import org.junit.Test

class ReqConsentApplyTest {
    @Test
    fun encode_ReqConsentApply_from_JSON() {
        val moshi: Moshi = Moshi.Builder().build()
        val adapter: JsonAdapter<ReqConsentApply> = moshi.adapter(ReqConsentApply::class.java)
        val json =
            "{\"source\":\"source\",\"destination\":{\"paths\":[\"*\"],\"uses\":[\"*\"]}}"
        val req = adapter.fromJson(json)
        Assert.assertEquals(req!!.destination.paths[0], "*")
        Assert.assertEquals(req.source, "source")
    }

    @Test
    fun encode_ReqConsentApply_to_JSON() {
        val moshi: Moshi = Moshi.Builder().build()
        val adapter: JsonAdapter<ReqConsentApply> = moshi.adapter(ReqConsentApply::class.java)
        val req = ReqConsentApply("source", TikiSdkDestination.ALL)
        val json = adapter.toJson(req)
        Assert.assertEquals(
            "{\"source\":\"source\",\"destination\":{\"paths\":[\"*\"],\"uses\":[\"*\"]}}",
            json
        )
    }
}