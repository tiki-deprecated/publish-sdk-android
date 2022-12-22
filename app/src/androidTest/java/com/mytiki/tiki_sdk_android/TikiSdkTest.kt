package com.mytiki.tiki_sdk_android

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

const val API_KEY: String = "2b8de004-cbe0-4bd5-bda6-b266d54f5c90"
const val ORIGIN: String = "com.mytiki.tiki_sdk_android.test"

@RunWith(AndroidJUnit4::class)
class TikiSdkTest {

    @Test
    fun init_tiki_sdk() {
        val context = getInstrumentation().targetContext
        val tikiSdk = TikiSdk(API_KEY, ORIGIN, context)
        assert(tikiSdk.address.length > 32)
    }

    @Test
    fun init_tiki_sdk_with_address() {
        val context = getInstrumentation().targetContext
        val tikiSdk = TikiSdk(API_KEY, ORIGIN, context)
        val address = tikiSdk.address
        val tikiSdk2 = TikiSdk(API_KEY, ORIGIN, context, address)
        assertEquals(tikiSdk.address, tikiSdk2.address)
    }

    @Test
    fun assign_onwership() {
        val context = getInstrumentation().targetContext
        val tikiSdk = TikiSdk(API_KEY, ORIGIN, context)
        val ownershipId = tikiSdk.assignOwnership("source", TikiSdkDataTypeEnum.data_point, listOf("test"))
        assert(ownershipId.length > 32)
    }

    @Test
    fun get_onwership() {
        val context = getInstrumentation().targetContext
        val tikiSdk = TikiSdk(API_KEY, ORIGIN, context)
        val ownershipId = tikiSdk.assignOwnership("source", TikiSdkDataTypeEnum.data_point, listOf("test"))
        val ownership = tikiSdk.getOwnership("source")
        assertEquals(ownershipId, ownership!!.transactionId)
    }

    @Test
    fun modify_consent() {
        val context = getInstrumentation().targetContext
        val tikiSdk = TikiSdk(API_KEY, ORIGIN, context)
        val ownershipId = tikiSdk.assignOwnership("source", TikiSdkDataTypeEnum.data_point, listOf("test"))
        val consent = tikiSdk.modifyConsent(ownershipId, TikiSdkDestination.ALL)
        assertEquals(consent.ownershipId, ownershipId)
    }

    fun get_consent() {
        val context = getInstrumentation().targetContext
        val tikiSdk = TikiSdk(API_KEY, ORIGIN, context)
        val ownershipId = tikiSdk.assignOwnership("source", TikiSdkDataTypeEnum.data_point, listOf("test"))
        val consent = tikiSdk.modifyConsent(ownershipId, TikiSdkDestination.ALL)
        val consentGet = tikiSdk.getConsent("source")
        assertEquals(consent.transactionId, consentGet!!.transactionId)
    }

    @Test
    fun apply_consent() {
        val context = getInstrumentation().targetContext
        val tikiSdk = TikiSdk(API_KEY, ORIGIN, context)
        val ownershipId = tikiSdk.assignOwnership("source", TikiSdkDataTypeEnum.data_point, listOf("test"))
        tikiSdk.modifyConsent(ownershipId, TikiSdkDestination.ALL)
        var ok = false
        tikiSdk.applyConsent("source", TikiSdkDestination.ALL, {
            ok = true
        })
        assert(ok)
    }
}