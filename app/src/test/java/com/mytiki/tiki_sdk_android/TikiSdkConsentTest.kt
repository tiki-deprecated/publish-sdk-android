package com.mytiki.tiki_sdk_android

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import org.junit.Assert
import org.junit.Test

class TikiSdkConsentTest {
    @Test
    fun encode_TikiSdkConsent_from_JSON() {
        val moshi: Moshi = Moshi.Builder().build()
        val adapter: JsonAdapter<TikiSdkConsent> = moshi.adapter(TikiSdkConsent::class.java)
        val tikiSdkConsent = adapter.fromJson("{\"ownershipId\":\"ownership\",\"destination\":{\"paths\":[\"*\"],\"uses\":[\"*\"]},\"transactionId\":\"txnId\"}")
        Assert.assertEquals("ownership", tikiSdkConsent?.ownershipId)
        Assert.assertEquals("*", tikiSdkConsent?.destination?.uses?.get(0))
        Assert.assertEquals("txnId", tikiSdkConsent?.transactionId)
        Assert.assertNull(tikiSdkConsent?.about)
        Assert.assertNull(tikiSdkConsent?.reward)
        Assert.assertNull(tikiSdkConsent?.expiry)
    }
    @Test
    fun encode_TikiSdkConsent_to_JSON() {
        val moshi: Moshi = Moshi.Builder().build()
        val adapter: JsonAdapter<TikiSdkConsent> = moshi.adapter(TikiSdkConsent::class.java)
        val tikiSdkConsent = TikiSdkConsent(
            "ownership", TikiSdkDestination.ALL, "txnId")
        val json = adapter.toJson(tikiSdkConsent)
        Assert.assertEquals("{\"ownershipId\":\"ownership\",\"destination\":{\"paths\":[\"*\"],\"uses\":[\"*\"]},\"transactionId\":\"txnId\"}",json)
    }
}