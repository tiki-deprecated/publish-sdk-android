/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */
package com.mytiki.tiki_sdk_android

import com.squareup.moshi.JsonClass

/**
 * Use case for license.
 *
 */
@JsonClass(generateAdapter = true)
open class LicenseUsecase(value: String) {

    var value: String
        private set

    init {
        try {
            val licenseUseCaseEnum = TitleTagEnum.fromValue(value)
            this.value = licenseUseCaseEnum.value
        } catch (e: Exception) {
            this.value = "custom:$value"
        }
    }

    constructor(licenseUseCaseEnum: LicenseUsecaseEnum) : this(licenseUseCaseEnum.value)

    fun toJson(): String {
        return "\"$value\""
    }

    companion object {
        fun fromJson(json: String): LicenseUsecase {
            return LicenseUsecase(json.replace("\"", ""))
        }

        /**
         * Use case for license attribution.
         */
        val ATTRIBUTION = LicenseUsecase(LicenseUsecaseEnum.ATTRIBUTION)

        /**
         * Use case for license retargeting.
         */
        val RETARGETING = LicenseUsecase(LicenseUsecaseEnum.RETARGETING)

        /**
         * Use case for license personalization.
         */
        val PERSONALIZATION = LicenseUsecase(LicenseUsecaseEnum.PERSONALIZATION)

        /**
         * Use case for license AI training.
         */
        val AI_TRAINING = LicenseUsecase(LicenseUsecaseEnum.AI_TRAINING)

        /**
         * Use case for license distribution.
         */
        val DISTRIBUTION = LicenseUsecase(LicenseUsecaseEnum.DISTRIBUTION)

        /**
         * Use case for license analytics.
         */
        val ANALYTICS = LicenseUsecase(LicenseUsecaseEnum.ANALYTICS)

        /**
         * Use case for license support.
         */
        val SUPPORT = LicenseUsecase(LicenseUsecaseEnum.SUPPORT)
    }
}
