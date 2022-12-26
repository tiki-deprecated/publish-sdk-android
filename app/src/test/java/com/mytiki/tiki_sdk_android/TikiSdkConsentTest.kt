package com.mytiki.tiki_sdk_android

import com.mytiki.tiki_sdk_android.util.TimeStampToDateAdapter
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import org.junit.Assert
import org.junit.Test
import java.util.*

class TikiSdkConsentTest {
    @Test
    fun encode_TikiSdkConsent_from_JSON() {
        val moshi: Moshi = Moshi.Builder()
            .add(TimeStampToDateAdapter())
            .build()
        val adapter: JsonAdapter<TikiSdkConsent> = moshi.adapter(TikiSdkConsent::class.java)
        val tikiSdkConsent =
            adapter.fromJson("{\"ownershipId\":\"ownership\",\"destination\":{\"paths\":[\"*\"],\"uses\":[\"*\"]},\"transactionId\":\"txnId\",\"expiry\":1671842900000}")
        Assert.assertEquals("ownership", tikiSdkConsent?.ownershipId)
        Assert.assertEquals("*", tikiSdkConsent?.destination?.uses?.get(0))
        Assert.assertEquals("txnId", tikiSdkConsent?.transactionId)
        Assert.assertEquals(1671842900000L, tikiSdkConsent?.expiry?.time)
        Assert.assertNull(tikiSdkConsent?.about)
        Assert.assertNull(tikiSdkConsent?.reward)
    }

    @Test
    fun encode_TikiSdkConsent_to_JSON() {
        val moshi: Moshi = Moshi.Builder()
            .add(TimeStampToDateAdapter())
            .build()
        val adapter: JsonAdapter<TikiSdkConsent> = moshi.adapter(TikiSdkConsent::class.java)
        val tikiSdkConsent = TikiSdkConsent(
            "ownership", TikiSdkDestination.ALL, "txnId", null, null, Date(1671842900000L)
        )

        val json = adapter.toJson(tikiSdkConsent)
        Assert.assertEquals(
            "{\"ownershipId\":\"ownership\",\"destination\":{\"paths\":[\"*\"],\"uses\":[\"*\"]},\"transactionId\":\"txnId\",\"expiry\":1671842900000}",
            json
        )
    }
}