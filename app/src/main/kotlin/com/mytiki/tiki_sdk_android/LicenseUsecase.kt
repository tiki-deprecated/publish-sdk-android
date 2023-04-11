/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */
package com.mytiki.tiki_sdk_android

/**
 * Use case for license.
 *
 */
open class LicenseUsecase(value: String) {

    var value: String
        private set

    init {
        try {
            val licenseUseCaseEnum = LicenseUsecaseEnum.fromValue(value)
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
            val value = json.replace("\"", "")
            return LicenseUsecase(value)
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
