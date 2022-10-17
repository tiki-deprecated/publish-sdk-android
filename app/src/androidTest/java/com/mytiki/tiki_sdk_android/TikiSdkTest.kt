package com.mytiki.tiki_sdk_android

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import com.mytiki.tiki_sdk_flutter_plugin.TikiSdkDestination
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class TikiSdkTest {
    private val apiKey: String = "a49fe762-124e-4ced-9b88-9814d64c131b"

    @Test
    fun init_tiki_sdk() {
        getInstrumentation().runOnMainSync {
            val context = getInstrumentation().targetContext
            TikiSdk(context, apiKey)
            assertEquals(1, 1)
        }
    }

    @Test
    fun assign_onwership() {
        getInstrumentation().runOnMainSync {
            val context = getInstrumentation().targetContext
            val sdk = TikiSdk(context, apiKey)
            sdk.assignOwnership("com.mytiki.test", "pool", listOf("email"))
            assertEquals(1, 1)
        }
    }

    @Test
    fun give_consent() {
        getInstrumentation().runOnMainSync {
            val context = getInstrumentation().targetContext
            val sdk = TikiSdk(context, apiKey)
            sdk.assignOwnership("com.mytiki.test", "pool", listOf("email"))
            sdk.modifyConsent("com.mytiki.test", TikiSdkDestination(listOf("*"),listOf("*")))
            assertEquals(1,1)
        }
    }

    @Test
    fun modify_consent() {
        getInstrumentation().runOnMainSync {
            val context = getInstrumentation().targetContext
            val sdk = TikiSdk(context, apiKey)
            sdk.assignOwnership("com.mytiki.test", "pool", listOf("email"))
            sdk.modifyConsent("com.mytiki.test", TikiSdkDestination(listOf("*"),listOf("*")))
            sdk.modifyConsent("com.mytiki.test", TikiSdkDestination(listOf(),listOf()))
            assertEquals(1,1)
        }
    }

    @Test
    fun apply_consent() {
        getInstrumentation().runOnMainSync {
            var ok = false
            val context = getInstrumentation().targetContext
            val sdk = TikiSdk(context, apiKey)
            sdk.assignOwnership("com.mytiki.test", "pool", listOf("email"))
            sdk.modifyConsent("com.mytiki.test", TikiSdkDestination(listOf("*"),listOf("*")))
            sdk.applyConsent("com.mytiki.test", TikiSdkDestination(listOf("*"),listOf("*")),
                "apply",
                request = {
                    ok = true
                },
                onBlock = {
                    ok = false
                })
            Thread.sleep(1000)
            assertEquals(true, ok)
        }
    }
}