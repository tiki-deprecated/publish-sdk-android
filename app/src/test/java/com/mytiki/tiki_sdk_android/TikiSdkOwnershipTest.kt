package com.mytiki.tiki_sdk_android

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import org.junit.Assert
import org.junit.Test

class TikiSdkOwnershipTest {
    @Test
    fun encode_TikiSdkOwnership_from_JSON() {
        val moshi: Moshi = Moshi.Builder().build()
        val adapter: JsonAdapter<TikiSdkOwnership> = moshi.adapter(TikiSdkOwnership::class.java)
        val json = "{\"source\":\"source\",\"type\":\"data_point\",\"origin\":\"com.mytiki.test\",\"transactionId\":\"txnId\",\"contains\":[\"test data\"]}"
        val ownership = adapter.fromJson(json)
        Assert.assertEquals("source", ownership?.source)
        Assert.assertEquals(TikiSdkDataTypeEnum.data_point.name, ownership?.type?.name)
        Assert.assertEquals("com.mytiki.test", ownership?.origin)
        Assert.assertEquals("txnId", ownership?.transactionId)
        Assert.assertNull(ownership?.about)

    }
    @Test
    fun encode_TikiSdkOwnership_to_JSON() {
        val moshi: Moshi = Moshi.Builder().build()
        val adapter: JsonAdapter<TikiSdkOwnership> = moshi.adapter(TikiSdkOwnership::class.java)
        val tikiSdkConsent = TikiSdkOwnership("source", TikiSdkDataTypeEnum.data_point,
            "com.mytiki.test", "txnId", listOf("test data"))
        val json = adapter.toJson(tikiSdkConsent)
        Assert.assertEquals("{\"source\":\"source\",\"type\":\"data_point\",\"origin\":\"com.mytiki.test\",\"transactionId\":\"txnId\",\"contains\":[\"test data\"]}",json)
    }
}