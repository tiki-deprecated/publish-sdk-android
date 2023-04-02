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

    /**
     * Use case for license attribution.
     */
    object ATTRIBUTION : LicenseUsecase(LicenseUsecaseEnum.ATTRIBUTION)

    /**
     * Use case for license retargeting.
     */
    object RETARGETING : LicenseUsecase(LicenseUsecaseEnum.RETARGETING)

    /**
     * Use case for license personalization.
     */
    object PERSONALIZATION : LicenseUsecase(LicenseUsecaseEnum.PERSONALIZATION)

    /**
     * Use case for license AI training.
     */
    object AI_TRAINING : LicenseUsecase(LicenseUsecaseEnum.AI_TRAINING)

    /**
     * Use case for license distribution.
     */
    object DISTRIBUTION : LicenseUsecase(LicenseUsecaseEnum.DISTRIBUTION)

    /**
     * Use case for license analytics.
     */
    object ANALYTICS : LicenseUsecase(LicenseUsecaseEnum.ANALYTICS)

    /**
     * Use case for license support.
     */
    object SUPPORT : LicenseUsecase(LicenseUsecaseEnum.SUPPORT)

}
