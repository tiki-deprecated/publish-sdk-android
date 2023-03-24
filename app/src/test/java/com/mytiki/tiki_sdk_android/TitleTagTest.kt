/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.tiki_sdk_android
import com.mytiki.tiki_sdk_android.core.req.ReqBuild
import com.mytiki.tiki_sdk_android.core.rsp.RspBuild
import com.mytiki.tiki_sdk_android.util.LicenseUsecaseFromStringAdapter
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import org.junit.Assert
import org.junit.Test

class LicenseUsecaseTest {
    @Test
    fun licenseUsecaseEnum_from_value() {
        val licenseUsecaseEnum = LicenseUsecaseEnum.fromValue("support")
        assert(licenseUsecaseEnum == LicenseUsecaseEnum.SUPPORT)
    }

    @Test
    fun licenseUsecase_from_value() {
        val licenseUsecase = LicenseUsecase("support")
        assert(licenseUsecase.value == LicenseUsecaseEnum.SUPPORT.value)
    }

    @Test
    fun licenseUsecase_from_json() {
        val moshi: Moshi = Moshi.Builder()
            .add(LicenseUsecaseFromStringAdapter())
            .build()
        val adapter: JsonAdapter<LicenseUsecase> = moshi.adapter(LicenseUsecase::class.java)
        val json = "\"support\""
        val licenseUsecase = adapter.fromJson(json)
        Assert.assertEquals(LicenseUsecase.SUPPORT.value, licenseUsecase?.value)
    }

    @Test
    fun licenseUsecase_to_json() {
        val moshi: Moshi = Moshi.Builder()
            .add(LicenseUsecaseFromStringAdapter())
            .build()
        val adapter: JsonAdapter<LicenseUsecase> = moshi.adapter(LicenseUsecase::class.java)
        val licenseUsecase = LicenseUsecase.SUPPORT
        val json = adapter.toJson(licenseUsecase)
        Assert.assertEquals("\"support\"", json)
    }
}