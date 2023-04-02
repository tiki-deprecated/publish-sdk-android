/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.tiki_sdk_android.integration_tests
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import com.mytiki.tiki_sdk_android.TikiSdk
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*
import kotlin.collections.ArrayList

@RunWith(AndroidJUnit4::class)
@LargeTest
class IntegrationTests {

    private val origin = "com.mytiki.iostest"
    private val publishingId = "e12f5b7b-6b48-4503-8b39-28e4995b5f88"
    private val id = UUID.randomUUID().toString()

    @Test
    fun testInitSdk() {
        runBlocking {
            try {
                TikiSdk.init(
                    InstrumentationRegistry.getInstrumentation().context,
                    publishingId,
                    id,
                    {
                        Assert.assertTrue(TikiSdk.isInitialized)
                    }
                )
            } catch (e: Exception) {
                Assert.fail(e.message)
            }
        }
    }

    @Test
    fun testTikiSdkConfig() {
        runBlocking {
            try {
                TikiSdk.config()
                    .theme
                    .primaryTextColor(Color.WHITE)
                    .primaryBackgroundColor(Color.WHITE)
                    .secondaryBackgroundColor(Color.WHITE)
                    .accentColor(Color.WHITE)
                    .fontFamily("test")
                    .and()
                    .dark
                    .primaryTextColor(Color.WHITE)
                    .primaryBackgroundColor(Color.WHITE)
                    .secondaryBackgroundColor(Color.WHITE)
                    .accentColor(Color.WHITE)
                    .fontFamily("test")
                    .and()
                    .offer
                    .id("randomId")
                    .bullet("test 1", true)
                    .bullet("test 2", false)
                    .bullet("test 3", true)
                    .ptr("source")
                    .description("testing")
                    .use(listOf(LicenseUsecase(LicenseUsecaseEnum.SUPPORT)))
                    .tag(TitleTag(TitleTagEnum.ADVERTISING_DATA))
                    .duration(365 * 24 * 60 * 60)
                    .permission(Permission.CAMERA)
                    .terms("terms")
                    .add()
                    .offer
                    .id("randomId2")
                    .bullet("test 1", true)
                    .bullet("test 2", true)
                    .bullet("test 3", true)
                    .ptr("source2")
                    .description("testing")
                    .use(listOf(LicenseUsecase(LicenseUsecaseEnum.SUPPORT)))
                    .tag(TitleTag(TitleTagEnum.ADVERTISING_DATA))
                    .duration(365 * 24 * 60 * 60)
                    .permission(Permission.CAMERA)
                    .terms("terms")
                    .add()
                    .onAccept { _, _ -> }
                    .onDecline { _, _ -> }
                    .onSettings { }
                    .disableAcceptEnding(false)
                    .disableDeclineEnding(true)
                    .initialize(
                        InstrumentationRegistry.getInstrumentation().context,
                        publishingId,
                        id,
                        {
                            Assert.assertTrue(TikiSdk.isInitialized)
                        }
                    )
            } catch (e: Exception) {
                Assert.fail(e.message)
            }
        }
    }

    @Test
    fun testLicense() {
        InstrumentationRegistry.getInstrumentation().runOnMainSync {
            try {
                TikiSdk.config().initialize(publishingId, id) {
                    try {
                        val offer = Offer()
                            .id("randomId")
                            .bullet("test 1", true)
                            .bullet("test 2", false)
                            .bullet("test 3", true)
                            .ptr("source")
                            .description("testing")
                            .terms("terms")
                            .use(getLicenseUsecases())
                            .tag(TitleTag(TitleTagEnum.ADVERTISING_DATA))
                            .permission(Permission.CAMERA)
                        val license = TikiSdk.license(
                            offer.ptr,
                            offer.uses,
                            offer.terms,
                            offer.tags,
                            offer.description,
                            offer.expiry
                        )
                        assertEquals("testing", license.description)
                        assertEquals(UseCaseEnum.SUPPORT, license.uses[0].usecases[0].value)
                        assertEquals(TitleTagEnum.ADVERTISING_DATA, license.title.tags[0].value)
                    } catch (e: Exception) {
                        fail()
                    }
                }
            } catch (e: Exception) {
                fail()
            }
        }
    }

    private fun getLicenseUsecases(): List<UseCase> {
        val usecases = ArrayList<UseCase>()
        usecases.add(UseCase(UseCaseEnum.SUPPORT))
        return usecases
    }
}
