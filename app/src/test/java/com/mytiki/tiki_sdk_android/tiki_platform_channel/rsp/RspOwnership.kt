/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */
package com.mytiki.tiki_sdk_android.tiki_platform_channel.rsp

import com.mytiki.tiki_sdk_android.TikiSdkDataTypeEnum
import com.mytiki.tiki_sdk_android.TikiSdkOwnership
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import org.junit.Assert
import org.junit.Test

class RspOwnershipTest {
    @Test
    fun encode_RspOwnership_from_JSON() {
        val moshi: Moshi = Moshi.Builder().build()
        val adapter: JsonAdapter<RspOwnership> = moshi.adapter(RspOwnership::class.java)
        val json =
            "{\"requestId\":\"requestId\",\"ownership\":{\"source\":\"source\",\"type\":\"data_point\",\"origin\":\"com.mytiki.rsp\",\"transactionId\":\"txnId\",\"contains\":[]}}"
        val req = adapter.fromJson(json)
        Assert.assertEquals("requestId", req!!.requestId)
    }

    @Test
    fun encode_RspOwnership_to_JSON() {
        val moshi: Moshi = Moshi.Builder().build()
        val adapter: JsonAdapter<RspOwnership> = moshi.adapter(RspOwnership::class.java)
        val rsp = RspOwnership(
            "requestId",
            TikiSdkOwnership("source", TikiSdkDataTypeEnum.data_point, "com.mytiki.rsp", "txnId")
        )
        val json = adapter.toJson(rsp)
        Assert.assertEquals(
            "{\"requestId\":\"requestId\",\"ownership\":{\"source\":\"source\",\"type\":\"data_point\",\"origin\":\"com.mytiki.rsp\",\"transactionId\":\"txnId\",\"contains\":[]}}",
            json
        )
    }
}