package com.mytiki.tiki_sdk_android

/**
 * Use case for license.
 *
 * @param licenseUseCaseEnum the license use case enumeration. Default value is null.
 * @constructor creates a new instance of LicenseUseCase with a [LicenseUseCaseEnum] predefined value.
 */
open class LicenseUseCase(private var licenseUseCaseEnum: LicenseUseCaseEnum? = null) {

    /**
     * Custom value to be used in LicenseUseCase.
     */
    private var customValue: String? = null

    /**
     * Creates a new instance of LicenseUseCase with custom value.
     *
     * @param customUseCase the custom use case for the license.
     */
    constructor(customUseCase: String) : this(){
        try{
            licenseUseCaseEnum = LicenseUseCaseEnum.fromValue(customUseCase)
        }catch (e: IllegalArgumentException) {
            customValue = "custom:$customUseCase"
        }
    }

    /**
     * Use case for license attribution.
     */
    object ATTRIBUTION : LicenseUseCase(LicenseUseCaseEnum.ATTRIBUTION)

    /**
     * Use case for license retargeting.
     */
    object RETARGETING : LicenseUseCase(LicenseUseCaseEnum.RETARGETING)

    /**
     * Use case for license personalization.
     */
    object PERSONALIZATION : LicenseUseCase(LicenseUseCaseEnum.PERSONALIZATION)

    /**
     * Use case for license AI training.
     */
    object AI_TRAINING : LicenseUseCase(LicenseUseCaseEnum.AI_TRAINING)

    /**
     * Use case for license distribution.
     */
    object DISTRIBUTION : LicenseUseCase(LicenseUseCaseEnum.DISTRIBUTION)

    /**
     * Use case for license analytics.
     */
    object ANALYTICS : LicenseUseCase(LicenseUseCaseEnum.ANALYTICS)

    /**
     * Use case for license support.
     */
    object SUPPORT : LicenseUseCase(LicenseUseCaseEnum.SUPPORT)

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
