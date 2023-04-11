/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.tiki_sdk_android

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
        val json = "\"support\""
        val licenseUsecase = LicenseUsecase.fromJson(json)
        Assert.assertEquals(LicenseUsecase.SUPPORT.value, licenseUsecase.value)
    }

    @Test
    fun licenseUsecase_to_json() {
        val licenseUsecase = LicenseUsecase.SUPPORT
        val json = licenseUsecase.toJson()
        Assert.assertEquals("\"support\"", json)
    }
}