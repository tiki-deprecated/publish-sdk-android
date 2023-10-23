/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.tiki_sdk_android

import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import io.mockk.mockkClass
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.async
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@LargeTest
class TikiSdkTest {
    @Test
    fun testInitSdk() {
        MainScope().async{
            val context: Context = InstrumentationRegistry.getInstrumentation().context
            var worked: Boolean = false
            TikiSdk.initialize("testId", "testPublishingId", context){worked = true}.await()
            assert(worked)
        }

    }
}
