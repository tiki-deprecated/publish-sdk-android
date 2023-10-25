/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.example.integration_tests

import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import com.mytiki.tiki_sdk_android.*
import com.mytiki.tiki_sdk_android.trail.Tag
import com.mytiki.tiki_sdk_android.trail.TagCommon
import com.mytiki.tiki_sdk_android.trail.Use
import com.mytiki.tiki_sdk_android.trail.UsecaseCommon
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.async
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.runner.RunWith
import java.lang.Error
import java.util.*

@RunWith(AndroidJUnit4::class)
@LargeTest
class IntegrationTests {
    private val context: Context = InstrumentationRegistry.getInstrumentation().targetContext
    private val publishingId = "862e2adb-a651-4218-967d-762c10092764"

    @Test
    fun testInitSdk() = TestScope().runTest {
        var isInit = false
        TikiSdk.initialize(UUID.randomUUID().toString(), publishingId, context){isInit = true}.await()
        assert(isInit)
        assert(TikiSdk.trail.isInitialized().await())
    }

    @Test
    fun testTitle() = TestScope().runTest {
        TikiSdk.initialize(UUID.randomUUID().toString(), publishingId, context).await()

        val pointerRecord = UUID.randomUUID().toString()
        val tagList = listOf(Tag.from(TagCommon.ADVERTISING_DATA.name))

        val createTitle = TikiSdk.trail.title.create(pointerRecord, tagList).await()
        val getTitle = TikiSdk.trail.title.get(pointerRecord).await()

        try {
            val duplicate = TikiSdk.trail.title.create(pointerRecord, tagList).await()
            assert(false)
        }catch (error: Error){
            assert(true)
        }

        assert(createTitle.hashedPtr == getTitle.hashedPtr)
        assert(createTitle.id == getTitle.id)
        assert(createTitle.description == getTitle.description)
        assert(createTitle.origin == getTitle.origin)
        assert(createTitle.tags.size == getTitle.tags.size)
    }

    @Test
    fun testLicense() = TestScope().runTest {
        TikiSdk.initialize(UUID.randomUUID().toString(), publishingId, context).await()

        val pointerRecord = UUID.randomUUID().toString()
        val tagList = listOf(Tag.from(TagCommon.ADVERTISING_DATA.name))
        val title =  TikiSdk.trail.title.create(pointerRecord, tagList).await()

        val titleId = title.id
        val use = Use.from(mapOf(
            "usecases" to listOf(UsecaseCommon.ATTRIBUTION.value),
        ))

        val createLicense = TikiSdk.trail.license.create(titleId, listOf(use), UUID.randomUUID().toString()).await()
        val getLicense = TikiSdk.trail.license.get(createLicense.id).await()

        assert(createLicense.id == getLicense.id)
        assert(createLicense.title.id == getLicense.title.id)
        assert(createLicense.uses.size == getLicense.uses.size)
        assert(createLicense.terms == getLicense.terms)
        assert(createLicense.description == getLicense.description)
        assert(createLicense.expiry == getLicense.expiry)
    }

    @Test
    fun testPayable() = TestScope().runTest {
        TikiSdk.initialize(UUID.randomUUID().toString(), publishingId, context).await()

        val licenseId = "Test licenseId"

        val createResult = TikiSdk.trail.payable.create(licenseId, "1.00", "Test type").await()
        val getResult = TikiSdk.trail.payable.get(licenseId).await()

        assert(createResult.id == getResult.id)
        assert(createResult.license == getResult.license)
        assert(createResult.amount == getResult.amount)
        assert(createResult.type == getResult.type)
        assert(createResult.description == getResult.description)
        assert(createResult.expiry == getResult.expiry)
        assert(createResult.reference == getResult.reference)
    }

    @Test
    fun testReceipt() = TestScope().runTest {
        TikiSdk.initialize(UUID.randomUUID().toString(), publishingId, context).await()

        val payableId = "Test payableId"

        val createResult = TikiSdk.trail.receipt.create(payableId, "1.00").await()
        val getResult = TikiSdk.trail.receipt.get(payableId).await()

        assert(createResult.id == getResult.id)
        assert(createResult.payable == getResult.payable)
        assert(createResult.amount == getResult.amount)
        assert(createResult.description == getResult.description)
        assert(createResult.reference == getResult.reference)
    }

    @Test
    fun testIdp() {
        MainScope().async{
            TikiSdk.initialize(UUID.randomUUID().toString(), publishingId, context).await()

            val keyId = "Test keyId"

            val exportTest = TikiSdk.idp.export(keyId).await()
        }
    }

    @Test
    fun testTrail() {
        MainScope().async{
            TikiSdk.initialize(UUID.randomUUID().toString(), publishingId, context).await()
            assert(TikiSdk.trail.isInitialized().await())

            val payableId = "Test payableId"
            val addressResult = TikiSdk.trail.address().await()
            val idResult = TikiSdk.trail.address().await()

            val titleId = "Test titleId"
            val use = Use.from(mapOf(
                "usecases" to UsecaseCommon.ATTRIBUTION.value,
            ))

            val licenseRecord = TikiSdk.trail.license.create(titleId, listOf(use), "Test Terms").await()


        }
    }

}
