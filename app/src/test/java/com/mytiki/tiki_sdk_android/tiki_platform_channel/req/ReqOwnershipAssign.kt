/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */
package com.mytiki.tiki_sdk_android.tiki_platform_channel.req

import com.mytiki.tiki_sdk_android.TikiSdkDataTypeEnum
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import org.junit.Assert
import org.junit.Test

class ReqOwnershipAssignTest {
    @Test
    fun encode_ReqOwnershipAssign_from_JSON() {
        val moshi: Moshi = Moshi.Builder().build()
        val adapter: JsonAdapter<ReqOwnershipAssign> = moshi.adapter(ReqOwnershipAssign::class.java)
        val json =
            "{\"source\":\"source\",\"type\":\"data_point\",\"contains\":[\"testData\"]}"
        val req = adapter.fromJson(json)
        Assert.assertEquals(req!!.source, "source")
    }

    @Test
    fun encode_ReqOwnershipAssign_to_JSON() {
        val moshi: Moshi = Moshi.Builder().build()
        val adapter: JsonAdapter<ReqOwnershipAssign> = moshi.adapter(ReqOwnershipAssign::class.java)
        val req = ReqOwnershipAssign(
            "source",
            TikiSdkDataTypeEnum.data_point,
            listOf("testData")
        )
        val json = adapter.toJson(req)
        Assert.assertEquals(
            "{\"source\":\"source\",\"type\":\"data_point\",\"contains\":[\"testData\"]}",
            json
        )
    }
}