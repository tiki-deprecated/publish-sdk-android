/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.tiki_sdk_android.integration_tests
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import com.mytiki.tiki_sdk_android.*
import com.mytiki.tiki_sdk_android.ui.Offer
import com.mytiki.tiki_sdk_android.ui.Permission
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Assert.fail
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

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
                TikiSdk.init(
                    context,
                    publishingId,
                    id
                ) {
                    Assert.assertTrue(TikiSdk.isInitialized)
                }
            } catch (e: Exception) {
                fail(e.message)
            }
        }
    }

    @Test
    fun testTikiSdkConfig() {
        runBlocking {
            try {
                TikiSdk.offer
                    .id("test_offer")
                    .ptr("test_offer")
                    .reward("offerImage")
                    .bullet("Learn how our ads perform ", true)
                    .bullet("Reach you on other platforms", false)
                    .bullet("Sold to other companies", false)
                    .use(listOf(LicenseUsecase(LicenseUsecaseEnum.SUPPORT)))
                    .permission(Permission.CAMERA)
                    .tag(TitleTag.ADVERTISING_DATA)
                    .description("Trade your IDFA (kind of like a serial # for your phone) for a discount.")
                    .terms(context, "terms.md")
                    .duration(365 * 24 * 60 * 60)
                    .add()
                    .onAccept { _, _ -> print("accepted") }
                    .onDecline { _, _ -> print("declined") }
                    //.onSettings  { print("settings") }
                    .disableAcceptEnding(false)
                    .disableDeclineEnding(false)
                    .init(context, "e12f5b7b-6b48-4503-8b39-28e4995b5f88", "user_123") {
                        Assert.assertTrue(TikiSdk.isInitialized)
                    }
            } catch (e: Exception) {
                fail(e.message)
            }
        }
    }

    @Test
    fun testLicense() {
        InstrumentationRegistry.getInstrumentation().runOnMainSync {
            MainScope().async {
                try {
                    TikiSdk.init(context, publishingId, id) {
                        val offer = Offer()
                            .id("randomId")
                            .bullet("test 1", true)
                            .bullet("test 2", false)
                            .bullet("test 3", true)
                            .ptr("source")
                            .description("testing")
                            .terms(context, "terms.md")
                            .use(getLicenseUsecases())
                            .tag(TitleTag(TitleTagEnum.ADVERTISING_DATA))
                            .permission(Permission.CAMERA)
                        runBlocking {
                            val licenseRecord: LicenseRecord = TikiSdk.license(
                                offer.ptr,
                                offer.uses,
                                offer.terms,
                                offer.tags,
                                null,
                                offer.description,
                                offer.expiry
                            ).await()
                            assertEquals("testing", licenseRecord.description)
                            assertEquals(
                                LicenseUsecaseEnum.SUPPORT,
                                licenseRecord.uses[0].usecases[0].value
                            )
                            assertEquals(
                                TitleTagEnum.ADVERTISING_DATA,
                                licenseRecord.title.tags[0].value
                            )
                        }
                    }
                    } catch (e: Exception) {
                        fail()
                    }
                }
            }
        }

    private fun getLicenseUsecases(): List<LicenseUsecase> {
        val usecases = ArrayList<LicenseUsecase>()
        usecases.add(LicenseUsecase(LicenseUsecaseEnum.SUPPORT))
        return usecases
    }
}
