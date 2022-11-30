package com.mytiki.tiki_sdk_android

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import kotlinx.coroutines.*
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

const val API_KEY: String = "2b8de004-cbe0-4bd5-bda6-b266d54f5c90"
const val ORIGIN: String = "com.mytiki.tiki_sdk_android.test"

@RunWith(AndroidJUnit4::class)
class TikiSdkTest {

    val callback = {
            response: String? -> print(response)
    }

    @Test
    fun init_tiki_sdk() {
        getInstrumentation().runOnMainSync {
            val context = getInstrumentation().targetContext
            val tikiSdk = TikiSdk(API_KEY, ORIGIN, context)
            Assert.assertNotNull(tikiSdk)
        }
    }

    @Test
    fun assign_onwership() {
        getInstrumentation().runOnMainSync {
            val context = getInstrumentation().targetContext
            val tikiSdk = TikiSdk(API_KEY, ORIGIN, context)
            tikiSdk.assignOwnership(
                "com.mytiki.androidtest",
                "data_point", callback, listOf("nothing")
            )
        }
    }

    @Test
    fun give_consent() {
        getInstrumentation().runOnMainSync {
            val context = getInstrumentation().targetContext
            val tikiSdk = TikiSdk(ORIGIN, API_KEY, context)
            tikiSdk.assignOwnership("com.mytiki.test", "pool")
            tikiSdk.modifyConsent("com.mytiki.test", TikiSdkDestination(listOf("*"), listOf("*")))
            assertEquals(1, 1)
        }
    }

    @Test
    fun modify_consent() {
        getInstrumentation().runOnMainSync {
            val context = getInstrumentation().targetContext
            val sdk = TikiSdk(API_KEY, ORIGIN, context)
            sdk.assignOwnership("com.mytiki.test", "pool")
            sdk.modifyConsent("com.mytiki.test", TikiSdkDestination(listOf("*"), listOf("*")))
            sdk.modifyConsent("com.mytiki.test", TikiSdkDestination(listOf(), listOf()))
            assertEquals(1, 1)
        }
    }

    @Test
    fun apply_consent() {
        getInstrumentation().runOnMainSync {
            var ok = false
            val context = getInstrumentation().targetContext
            val sdk = TikiSdk(API_KEY, ORIGIN, context)
            sdk.assignOwnership("com.mytiki.test", "pool")
            sdk.modifyConsent("com.mytiki.test", TikiSdkDestination(listOf("*"), listOf("*")))
            sdk.applyConsent("com.mytiki.test", TikiSdkDestination(listOf("*"), listOf("*")),
                {
                    ok = true
                },
                {
                    ok = false
                })
            Thread.sleep(1000)
        }
    }
}