package com.mytiki.tiki_sdk_android.integration_tests

import androidx.annotation.UiThread
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.internal.runner.junit4.statement.UiThreadStatement
import androidx.test.platform.app.InstrumentationRegistry
import com.mytiki.tiki_sdk_android.TikiSdk
import com.mytiki.tiki_sdk_android.TikiSdkDataTypeEnum
import com.mytiki.tiki_sdk_android.TikiSdkDestination
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class TikiSdkTest {

    private val apiId: String = "2b8de004-cbe0-4bd5-bda6-b266d54f5c90"
    private val origin: String = "com.mytiki.tiki_sdk_android.test"

    @Test
    fun init_tiki_sdk() {
        runBlocking {
            val context = InstrumentationRegistry.getInstrumentation().targetContext
            val tikiSdk = TikiSdk().init(apiId, origin, context).await()
            assert(tikiSdk.address.length > 32)
        }
    }
//
//    @Test
//    fun init_tiki_sdk_with_address() {
//        runBlocking{
//            val context = InstrumentationRegistry.getInstrumentation().targetContext
//            val tikiSdk = TikiSdk(apiKey, origin, context)
//            val address = tikiSdk.address
//            val tikiSdk2 = TikiSdk(apiKey, origin, context, address)
//            Assert.assertEquals(tikiSdk.address, tikiSdk2.address)
//        }
//    }
//
//    @Test
//    fun assign_onwership() {
//        runBlocking{
//            val context = InstrumentationRegistry.getInstrumentation().targetContext
//            val tikiSdk = TikiSdk(apiKey, origin, context)
//            val ownershipId = tikiSdk.assignOwnership("source", TikiSdkDataTypeEnum.data_point, listOf("test"))
//            assert(ownershipId.length > 32)
//        }
//    }
//
//    @Test
//    fun get_onwership() {
//        runBlocking {
//            val context = InstrumentationRegistry.getInstrumentation().targetContext
//            val tikiSdk = TikiSdk(apiKey, origin, context)
//            val ownershipId = tikiSdk.assignOwnership("source", TikiSdkDataTypeEnum.data_point, listOf("test"))
//            val ownership = tikiSdk.getOwnership("source")
//            Assert.assertEquals(ownershipId, ownership!!.transactionId)
//        }
//    }
//
//    @Test
//    fun modify_consent() {
//        runBlocking {
//            val context = InstrumentationRegistry.getInstrumentation().targetContext
//            val tikiSdk = TikiSdk(apiKey, origin, context)
//            val ownershipId = tikiSdk.assignOwnership("source", TikiSdkDataTypeEnum.data_point, listOf("test"))
//            val consent = tikiSdk.modifyConsent(ownershipId, TikiSdkDestination.ALL)
//            Assert.assertEquals(consent.ownershipId, ownershipId)
//        }
//    }
//
//    @Test
//    fun get_consent() {
//        runBlocking {
//            val context = InstrumentationRegistry.getInstrumentation().targetContext
//            val tikiSdk = TikiSdk(apiKey, origin, context)
//            val ownershipId = tikiSdk.assignOwnership("source", TikiSdkDataTypeEnum.data_point, listOf("test"))
//            val consent = tikiSdk.modifyConsent(ownershipId, TikiSdkDestination.ALL)
//            val consentGet = tikiSdk.getConsent("source")
//            Assert.assertEquals(consent.transactionId, consentGet!!.transactionId)
//        }
//    }
//
//    @Test
//    fun apply_consent() {
//        runBlocking {
//            val context = InstrumentationRegistry.getInstrumentation().targetContext
//            val tikiSdk = TikiSdk(apiKey, origin, context)
//            val ownershipId = tikiSdk.assignOwnership("source", TikiSdkDataTypeEnum.data_point, listOf("test"))
//            tikiSdk.modifyConsent(ownershipId, TikiSdkDestination.ALL)
//            var ok = false
//            tikiSdk.applyConsent("source", TikiSdkDestination.ALL, {
//                ok = true
//            })
//            assert(ok)
//        }
//    }
}