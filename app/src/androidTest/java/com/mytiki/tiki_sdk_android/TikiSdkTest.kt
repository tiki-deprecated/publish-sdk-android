package com.mytiki.tiki_sdk_android

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class TikiSdkTest {
    private val apiKey : String = ""

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
        getInstrumentation().runOnMainSync{
            val context = getInstrumentation().targetContext
            val sdk = TikiSdk(context, apiKey)
            sdk.assignOwnership("com.mytiki.test", "pool", listOf("email"))
            assertEquals(1, 1)
        }
    }
    @Test
    fun give_consent() {
        getInstrumentation().runOnMainSync{
            val context = getInstrumentation().targetContext
        val sdk  = TikiSdk(context, apiKey)
        sdk.assignOwnership("com.mytiki.test", "pool", listOf("email"))
        }
    }

    @Test
    fun modify_consent() {
        getInstrumentation().runOnMainSync{
            val context = getInstrumentation().targetContext
        TikiSdk(context, apiKey)
        assertEquals(1,1)
        }
    }
    @Test
    fun apply_consent() {
        getInstrumentation().runOnMainSync {
            val context = getInstrumentation().targetContext
            TikiSdk( context, apiKey)
            assertEquals(1,1)
        }
    }
}