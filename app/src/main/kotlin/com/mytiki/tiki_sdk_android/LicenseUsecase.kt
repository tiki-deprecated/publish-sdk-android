/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.tiki_sdk_android

import com.squareup.moshi.JsonClass

/**
 * Use case for license.
 */
@JsonClass(generateAdapter = true)
class LicenseUsecase(value: String){

    var value: String
        private set

    init{
        try {
            val licenseUsecaseEnum = LicenseUsecaseEnum.fromValue(value)
            this.value = licenseUsecaseEnum.value
        } catch (e: Exception) {
            this.value = "custom:$value"
        }
    }

    constructor(licenseUsecaseEnum: LicenseUsecaseEnum) : this(licenseUsecaseEnum.value)

    companion object {
        val ATTRIBUTION = LicenseUsecase(LicenseUsecaseEnum.ATTRIBUTION)
        val RETARGETING = LicenseUsecase(LicenseUsecaseEnum.RETARGETING)
        val PERSONALIZATION = LicenseUsecase(LicenseUsecaseEnum.PERSONALIZATION)
        val AI_TRAINING = LicenseUsecase(LicenseUsecaseEnum.AI_TRAINING)
        val DISTRIBUTION = LicenseUsecase(LicenseUsecaseEnum.DISTRIBUTION)
        val ANALYTICS = LicenseUsecase(LicenseUsecaseEnum.ANALYTICS)
        val SUPPORT = LicenseUsecase(LicenseUsecaseEnum.SUPPORT)
    }

}

