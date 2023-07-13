/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.tiki_sdk_android.integration_tests

import android.util.Log
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import com.mytiki.tiki_sdk_android.*
import com.mytiki.tiki_sdk_android.idp.Token
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.junit.Assert
import org.junit.Assert.assertTrue
import org.junit.Assert.fail
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*

@RunWith(AndroidJUnit4::class)
@LargeTest
class IntegrationTests {

    private val publishingId = "e12f5b7b-6b48-4503-8b39-28e4995b5f88"
    private val context = InstrumentationRegistry.getInstrumentation().targetContext
    private val id = UUID.randomUUID().toString()

    @Test
    fun testInitSdk() {
        runBlocking {
            try {
                TikiSdk.initialize(
                    "9d1c6ca9-2bb9-4c13-8457-83e313c72d88",
                    "be19730a-00d5-45f5-b18e-2e19eb25f311",
                    context
                ) {
                    Log.d("TIKI", "WOAH!")
                    assertTrue(TikiSdk.isInitialized)
                }.await()
            } catch (e: Exception) {
                fail(e.message)
            }
            withContext(MainScope().coroutineContext) {
                val token: Token = TikiSdk.idp.token()
                Assert.assertEquals(token.scope?.size, 2)
            }
        }
    }
}
