package com.mytiki.tiki_sdk_android

/**
 * Title tag
 *
 * Tags are included in the [TitleRecord] and describe the represented data asset.
 * Tags improve record searchability and come in handy when bulk searching and filtering licenses.
 * Use either our list of common enumerations or define your own using [customValue] as constructor
 * parameter.
 *
 * @property titleTagEnum
 * @constructor Create empty Title tag
 */
open class TitleTag(private var titleTagEnum: TitleTagEnum? = null) {

    /**
     * Custom value to be used in TitleTag.
     */
    private var customValue: String? = null

    /**
     * Creates a new instance of TitleTag with custom value.
     *
     * @param customTag the custom tag.
     */
    constructor(customTag: String) : this() {
        try {
            titleTagEnum = TitleTagEnum.fromValue(customTag)
        } catch (e: IllegalArgumentException) {
            customValue = "custom:$customTag"
        }
    }

    object EMAIL_ADDRESS : TitleTag(TitleTagEnum.EMAIL_ADDRESS)
    object PHONE_NUMBER : TitleTag(TitleTagEnum.PHONE_NUMBER)
    object PHYSICAL_ADDRESS : TitleTag(TitleTagEnum.PHYSICAL_ADDRESS)
    object CONTACT_INFO : TitleTag(TitleTagEnum.CONTACT_INFO)
    object HEALTH : TitleTag(TitleTagEnum.HEALTH)
    object FITNESS : TitleTag(TitleTagEnum.FITNESS)
    object PAYMENT_INFO : TitleTag(TitleTagEnum.PAYMENT_INFO)
    object CREDIT_INFO : TitleTag(TitleTagEnum.CREDIT_INFO)
    object FINANCIAL_INFO : TitleTag(TitleTagEnum.FINANCIAL_INFO)
    object PRECISE_LOCATION : TitleTag(TitleTagEnum.PRECISE_LOCATION)
    object COARSE_LOCATION : TitleTag(TitleTagEnum.COARSE_LOCATION)
    object SENSITIVE_INFO : TitleTag(TitleTagEnum.SENSITIVE_INFO)
    object CONTACTS : TitleTag(TitleTagEnum.CONTACTS)
    object MESSAGES : TitleTag(TitleTagEnum.MESSAGES)
    object PHOTO_VIDEO : TitleTag(TitleTagEnum.PHOTO_VIDEO)
    object AUDIO : TitleTag(TitleTagEnum.AUDIO)
    object GAMEPLAY_CONTENT : TitleTag(TitleTagEnum.GAMEPLAY_CONTENT)
    object CUSTOMER_SUPPORT : TitleTag(TitleTagEnum.CUSTOMER_SUPPORT)
    object USER_CONTENT : TitleTag(TitleTagEnum.USER_CONTENT)
    object BROWSING_HISTORY : TitleTag(TitleTagEnum.BROWSING_HISTORY)
    object SEARCH_HISTORY : TitleTag(TitleTagEnum.SEARCH_HISTORY)
    object USER_ID : TitleTag(TitleTagEnum.USER_ID)
    object DEVICE_ID : TitleTag(TitleTagEnum.DEVICE_ID)
    object PURCHASE_HISTORY : TitleTag(TitleTagEnum.PURCHASE_HISTORY)
    object PRODUCT_INTERACTION : TitleTag(TitleTagEnum.PRODUCT_INTERACTION)
    object ADVERTISING_DATA : TitleTag(TitleTagEnum.ADVERTISING_DATA)
    object USAGE_DATA : TitleTag(TitleTagEnum.USAGE_DATA)
    object CRASH_DATA : TitleTag(TitleTagEnum.CRASH_DATA)
    object PERFORMANCE_DATA : TitleTag(TitleTagEnum.PERFORMANCE_DATA)
    object DIAGNOSTIC_DATA : TitleTag(TitleTagEnum.DIAGNOSTIC_DATA)

    /**
     * Returns the license use case value.
     *
     * @return the license use case value, which can be either the value of licenseUseCaseEnum or customValue.
     * @throws NullPointerException if licenseUseCaseEnum is null and customValue is null.
     */
    val value: String
        get() {
            return titleTagEnum?.value ?: customValue!!
        }
}