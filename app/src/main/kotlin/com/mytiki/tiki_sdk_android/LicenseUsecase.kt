package com.mytiki.tiki_sdk_android

/**
 * Use case for license.
 *
 * @param licenseUseCaseEnum the license use case enumeration. Default value is null.
 * @constructor creates a new instance of LicenseUsecase with a [LicenseUsecaseEnum] predefined value.
 */
open class LicenseUsecase(private var licenseUseCaseEnum: LicenseUsecaseEnum? = null) {

    /**
     * Custom value to be used in LicenseUsecase.
     */
    private var customValue: String? = null

    /**
     * Creates a new instance of LicenseUsecase with custom value.
     *
     * @param customUseCase the custom use case for the license.
     */
    constructor(customUseCase: String) : this() {
        try {
            licenseUseCaseEnum = LicenseUsecaseEnum.fromValue(customUseCase)
        } catch (e: IllegalArgumentException) {
            customValue = "custom:$customUseCase"
        }
    }

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

    /**
     * Returns the license use case value.
     *
     * @return the license use case value, which can be either the value of licenseUseCaseEnum or customValue.
     * @throws NullPointerException if licenseUseCaseEnum is null and customValue is null.
     */
    val value: String
        get() {
            return licenseUseCaseEnum?.value ?: customValue!!
        }
}
