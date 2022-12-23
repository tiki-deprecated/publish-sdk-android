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

class ReqConsentModifyTest {
    @Test
    fun encode_ReqConsentModify_from_JSON() {
        val moshi: Moshi = Moshi.Builder().build()
        val adapter: JsonAdapter<ReqConsentModify> = moshi.adapter(ReqConsentModify::class.java)
        val json =
            "{\"ownershipId\":\"ownershipId\",\"destination\":{\"paths\":[\"*\"],\"uses\":[\"*\"]}}"
        val req = adapter.fromJson(json)
        Assert.assertEquals("*", req!!.destination.paths[0])
        Assert.assertEquals("ownershipId", req.ownershipId)
    }

    @Test
    fun encode_ReqConsentModify_to_JSON() {
        val moshi: Moshi = Moshi.Builder().build()
        val adapter: JsonAdapter<ReqConsentModify> = moshi.adapter(ReqConsentModify::class.java)
        val req = ReqConsentModify("ownershipId", TikiSdkDestination.ALL)
        val json = adapter.toJson(req)
        Assert.assertEquals(
            "{\"ownershipId\":\"ownershipId\",\"destination\":{\"paths\":[\"*\"],\"uses\":[\"*\"]}}",
            json
        )
    }
}